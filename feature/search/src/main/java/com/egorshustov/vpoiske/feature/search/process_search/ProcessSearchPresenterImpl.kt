package com.egorshustov.vpoiske.feature.search.process_search

import com.egorshustov.vpoiske.core.common.exceptions.NetworkException
import com.egorshustov.vpoiske.core.common.model.Result
import com.egorshustov.vpoiske.core.common.network.AppDispatchers.IO
import com.egorshustov.vpoiske.core.common.network.Dispatcher
import com.egorshustov.vpoiske.core.common.utils.*
import com.egorshustov.vpoiske.core.domain.search.GetSearchUseCase
import com.egorshustov.vpoiske.core.domain.search.GetSearchUseCaseParams
import com.egorshustov.vpoiske.core.domain.token.GetAccessTokenUseCase
import com.egorshustov.vpoiske.core.domain.user.*
import com.egorshustov.vpoiske.core.model.data.*
import com.egorshustov.vpoiske.core.model.data.requestsparams.*
import com.egorshustov.vpoiske.core.ui.api.UiMessageManager
import com.egorshustov.vpoiske.core.ui.util.WhileSubscribed
import com.egorshustov.vpoiske.core.ui.util.unwrapResult
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import timber.log.Timber

@OptIn(FlowPreview::class)
internal class ProcessSearchPresenterImpl @AssistedInject constructor(
    @Assisted("searchId") private val searchId: Long,
    @Assisted("parentJob") private val parentJob: Job,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
    private val getAccessTokenUseCase: GetAccessTokenUseCase,
    private val getSearchUseCase: GetSearchUseCase,
    getUsersCountUseCase: GetUsersCountUseCase,
    private val searchUsersUseCase: SearchUsersUseCase,
    private val saveUsersUseCase: SaveUsersUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val saveUserUseCase: SaveUserUseCase
) : ProcessSearchPresenter {

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        Timber.d("CoroutineExceptionHandler. $exception")
        cancelSearch()
    }

    private val presenterScope = CoroutineScope(ioDispatcher + parentJob + exceptionHandler)

    private val foundUsersCountFlow: StateFlow<Int> = getUsersCountUseCase(
        GetUsersCountUseCaseParams(searchId)
    ).mapNotNull { it.data }.stateIn(
        scope = presenterScope,
        started = WhileSubscribed,
        initialValue = 0
    )

    private val foundUsersLimitFlow: MutableStateFlow<Int?> = MutableStateFlow(null)

    private val isSearchCompletedFlow: StateFlow<Boolean> = combine(
        foundUsersCountFlow,
        foundUsersLimitFlow
    ) { foundUsersCount, foundUsersLimit ->
        foundUsersLimit != null && foundUsersCount >= foundUsersLimit
    }.stateIn(
        scope = presenterScope,
        started = SharingStarted.Lazily,
        initialValue = false
    )

    private val uiMessageManager = UiMessageManager()

    override val state: StateFlow<ProcessSearchState> = combine(
        foundUsersCountFlow,
        foundUsersLimitFlow,
        uiMessageManager.message,
    ) { foundUsersCount, foundUsersLimit, message ->
        ProcessSearchState(foundUsersCount, foundUsersLimit, message)
    }.stateIn(
        scope = presenterScope,
        started = WhileSubscribed,
        initialValue = ProcessSearchState.Empty
    )

    init {
        collectIsSearchCompleted()
    }

    override fun startSearch(): Job = presenterScope.launch {
        Timber.d("startSearch. searchId id: $searchId")
        val search = getSearchUseCase(GetSearchUseCaseParams(searchId)).data ?: return@launch
        foundUsersLimitFlow.update { search.foundUsersLimit }
        search.init()
    }

    private suspend fun Search.init() {
        while (!isSearchCompletedFlow.value) {
            val randomDay = (1..MAX_DAYS_IN_MONTH).random()
            val randomMonth = (1..MONTHS_IN_YEAR).random()
            Timber.d("Search.init. Searching users with birthday: $randomDay.$randomMonth")

            searchUsers(
                search = this@init,
                birthDay = randomDay,
                birthMonth = randomMonth
            ).join()
        }
    }

    private fun searchUsers(search: Search, birthDay: Int, birthMonth: Int): Job = getAccessToken()
        .flatMapMerge { accessToken ->
            getUsers(
                search = search,
                birthDay = birthDay,
                birthMonth = birthMonth,
                accessToken = accessToken
            ).filterUsers(search).flatMapConcat { filteredUsers ->
                if (search.isFriendsLimitSet) {
                    filteredUsers.fetchAdditionalDataAndProcess(search, accessToken)
                } else {
                    filteredUsers.saveUsers(search)
                }
            }
        }.launchIn(presenterScope)

    private fun getAccessToken(): Flow<String> = getAccessTokenUseCase(Unit)
        .mapNotNull { it.data }
        .filterNot { it.isEmpty() }
        .take(1)

    /**
     * Sending "users.search" request.
     * @param search current search entity for building request params.
     * @param birthDay search for users with this birthday.
     * @param birthMonth search for users with this birth month.
     * @param accessToken auth token which must be added to the request.
     * @return obtained users.
     */
    private fun getUsers(
        search: Search,
        birthDay: Int,
        birthMonth: Int,
        accessToken: String
    ): Flow<List<User>> = searchUsersUseCase(
        SearchUsersUseCaseParams(
            searchUsersParams = SearchUsersRequestParams(
                countryId = search.country.id,
                cityId = search.city.id,
                ageFrom = search.ageFrom,
                ageTo = search.ageTo,
                birthDay = birthDay,
                birthMonth = birthMonth,
                fields = if (search.isFriendsLimitSet) {
                    SEARCH_USERS_FRIENDS_LIMIT_SET_FIELDS
                } else {
                    SEARCH_USERS_FRIENDS_LIMIT_NOT_SET_FIELDS
                },
                homeTown = search.homeTown?.takeUnless { it.isBlank() },
                relationId = search.relation.id,
                genderId = search.gender.id,
                hasPhoto = true,
                count = MAX_POSSIBLE_USERS_COUNT,
                sortTypeId = UsersSortType.BY_REGISTRATION_DATE.id
            ),
            commonParams = VkCommonRequestParams(accessToken)
        )
    ).unwrapResult(
        uiMessageManager = uiMessageManager,
        onError = { if (it is NetworkException.VkException) cancelSearch() }
    ).onEach {
        delay(pauseDelay.toDuration()) // delay to avoid API flood blocking
    }

    /**
     * Filter users which were obtained with "users.search" request.
     * @param search current search entity with filtering params.
     * @return filtered users.
     */
    private fun Flow<List<User>>.filterUsers(search: Search): Flow<List<User>> =
        filterList { user ->
            val isNotClosed = user.permissions.isClosed == false
            val isInRequiredLocation = user.country?.id == search.country.id
                    && user.city?.id == search.city.id
            val isFollowersCountAcceptable =
                user.counters?.followers in search.followersMinCount..search.followersMaxCount
            val isInDaysInterval =
                search.startTime - (user.lastSeen?.time ?: UnixSeconds.Zero) <
                        UnixSeconds(search.daysInterval * SECONDS_IN_DAY)
            val phoneCheckPassed = if (search.withPhoneOnly) user.hasValidPhone else true

            isNotClosed && isInRequiredLocation && isFollowersCountAcceptable
                    && isInDaysInterval && phoneCheckPassed
        }

    private fun List<User>.fetchAdditionalDataAndProcess(
        search: Search,
        accessToken: String
    ): Flow<Result<Unit>> = asFlow().flatMapConcat {
        // We're sending requests sequentially to avoid API flood blocking:
        getUser(userId = it.id, accessToken = accessToken)
            .filterUser(search)
            .saveUserIfFiltered(search)
    }

    private fun getUser(userId: Long, accessToken: String): Flow<User> = getUserUseCase(
        GetUserUseCaseParams(
            getUserParams = GetUserRequestParams(
                userId = userId,
                fields = GET_USER_FIELDS
            ),
            commonParams = VkCommonRequestParams(accessToken)
        )
    ).unwrapResult(
        uiMessageManager = uiMessageManager,
        onError = { if (it is NetworkException.VkException) cancelSearch() }
    ).onEach {
        delay(pauseDelay.toDuration()) // delay to avoid API flood blocking
    }

    private fun Flow<User>.filterUser(search: Search): Flow<User> = filter { user ->
        val isFriendsCountAcceptable =
            user.counters?.friends in (search.friendsMinCount ?: 0)..
                    (search.friendsMaxCount ?: Int.MAX_VALUE)

        val isDesiredRelation = user.relation == search.relation
        isFriendsCountAcceptable && isDesiredRelation
    }

    private fun Flow<User>.saveUserIfFiltered(search: Search): Flow<Result<Unit>> = map { user ->
        user.apply {
            searchId = search.id
            foundTime = currentTime
        }
        saveUserUseCase(SaveUserUseCaseParams(user)).ignoreResultData()
    }

    private fun List<User>.saveUsers(search: Search): Flow<Result<Unit>> = suspend {
        apply {
            val time = currentTime
            forEach { user ->
                user.searchId = search.id
                user.foundTime = time
            }
        }
        saveUsersUseCase(SaveUsersUseCaseParams(this))
    }.asFlow().ignoreResultData()

    private fun collectIsSearchCompleted() {
        presenterScope.launch {
            isSearchCompletedFlow.collect { isSearchCompleted ->
                if (isSearchCompleted) cancelSearch()
            }
        }
    }

    private fun cancelSearch() {
        Timber.d("cancelSearch.")
        presenterScope.cancel()
    }

    override fun onMessageShown(uiMessageId: Long) {
        presenterScope.launch {
            uiMessageManager.clearMessage(uiMessageId)
        }
    }

    @AssistedFactory
    interface Factory {

        fun create(
            @Assisted("searchId") searchId: Long,
            @Assisted("parentJob") parentJob: Job
        ): ProcessSearchPresenterImpl
    }

    private companion object {

        private val pauseDelay: Millis = Millis(500)
    }
}