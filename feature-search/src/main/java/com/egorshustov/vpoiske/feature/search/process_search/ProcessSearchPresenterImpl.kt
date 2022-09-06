package com.egorshustov.vpoiske.feature.search.process_search

import com.egorshustov.vpoiske.core.common.model.*
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

    // This exception handler allows to catch non-cancellation exceptions
    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        Timber.d("exceptionHandler thread $currentThreadName")
        Timber.d("Caught exceptionHandler $exception")
        // todo: change state on exception
    }

    private val presenterScope = CoroutineScope(ioDispatcher + parentJob + exceptionHandler)

    private val foundUsersCountFlow: StateFlow<Int> = getUsersCountUseCase(
        GetUsersCountUseCaseParams(searchId)
    ).mapNotNull { it.data }
        .log("foundUsersCountFlow")
        .stateIn(
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
    }.log("isSearchCompletedFlow")
        .stateIn(
            scope = presenterScope,
            started = SharingStarted.Lazily,
            initialValue = false
        )

    private val uiMessageManager = UiMessageManager() // TODO replace with shared flow

    override val state: StateFlow<ProcessSearchState> = combine(
        foundUsersCountFlow,
        foundUsersLimitFlow,
        uiMessageManager.message,
    ) { foundUsersCount, foundUsersLimit, message ->
        ProcessSearchState(foundUsersCount, foundUsersLimit, message)
    }.log("ProcessSearchState")
        .stateIn(
            scope = presenterScope,
            started = WhileSubscribed,
            initialValue = ProcessSearchState.Empty
        )

    init {
        collectIsSearchCompleted()
    }

    override fun startSearch(): Job = presenterScope.launch {
        Timber.d("startSearch with id: $searchId")
        val search = getSearchUseCase(GetSearchUseCaseParams(searchId)).data ?: return@launch
        Timber.d("Search obtained from DB: $search")
        foundUsersLimitFlow.value = search.foundUsersLimit
        search.init()
    }.apply {
        invokeOnCompletion {
            Timber.d("invokeOnCompletion thread $currentThreadName")
            Timber.d("Caught invokeOnCompletion $it")
        }
    }

    private suspend fun Search.init() {
        Timber.d("Current isSearchCompletedFlow.value: ${isSearchCompletedFlow.value}")
        while (!isSearchCompletedFlow.value) {
            val randomDay = (1..MAX_DAYS_IN_MONTH).random()
            val randomMonth = (1..MONTHS_IN_YEAR).random()
            Timber.d("Search users with birthday: $randomDay.$randomMonth")

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
            ).filterUsers(search).concatMapResult { filteredUsers ->
                if (search.isFriendsLimitSet) {
                    filteredUsers.fetchAdditionalDataAndProcess(search, accessToken)
                } else {
                    filteredUsers.saveUsers(search)
                }
            }
        }.launchIn(presenterScope)

    private fun getAccessToken(): Flow<String> = getAccessTokenUseCase(Unit)
        .map { it.data.toString() }
        .filterNot { it.isEmpty() }
        .take(1)

    private fun getUsers(
        search: Search,
        birthDay: Int,
        birthMonth: Int,
        accessToken: String
    ): Flow<Result<List<User>>> = searchUsersUseCase(
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
    ).doOnResultSuccess {
        Timber.d("Let's wait for a ${pauseDelay.count} millis to avoid API flood blocking")
        delay(pauseDelay.toDuration())
    }

    private fun Flow<Result<List<User>>>.filterUsers(search: Search): Flow<Result<List<User>>> =
        mapResult { users ->
            Timber.d(
                "Filter users obtained with search request\n" +
                        "Search users count initial: ${users.size}"
            )
            users.filter { user ->
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
            }.also {
                Timber.d("Search users count filtered: ${it.size}")
            }
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

    private fun getUser(userId: Long, accessToken: String): Flow<Result<User>> =
        getUserUseCase(
            GetUserUseCaseParams(
                getUserParams = GetUserRequestParams(
                    userId = userId,
                    fields = GET_USER_FIELDS
                ),
                commonParams = VkCommonRequestParams(accessToken)
            )
        ).doOnResultSuccess {
            Timber.d("Let's wait for a ${pauseDelay.count} millis to avoid API flood blocking")
            delay(pauseDelay.toDuration())
        }

    private fun Flow<Result<User>>.filterUser(search: Search): Flow<Result<User>> =
        filterResult { user ->
            val isFriendsCountAcceptable =
                user.counters?.friends in (search.friendsMinCount ?: 0)..
                        (search.friendsMaxCount ?: Int.MAX_VALUE)

            val isDesiredRelation = user.relation == search.relation
            isFriendsCountAcceptable && isDesiredRelation
        }

    private fun Flow<Result<User>>.saveUserIfFiltered(search: Search): Flow<Result<Unit>> =
        doOnResultSuccess { user ->
            user.apply {
                searchId = search.id
                foundTime = currentTime
            }
            saveUserUseCase(SaveUserUseCaseParams(user))
        }.ignoreResultData()

    private fun List<User>.saveUsers(search: Search): Flow<Result<Unit>> = suspend {
        apply {
            val time = currentTime
            forEach { user ->
                user.searchId = search.id
                user.foundTime = time
            }
        }
        saveUsersUseCase(SaveUsersUseCaseParams(this)).also {
            Timber.d("Saved users count: ${it.data?.size}")
        }
    }.asFlow().ignoreResultData()

    private fun collectIsSearchCompleted() {
        presenterScope.launch {
            isSearchCompletedFlow.collect { isSearchCompleted ->
                Timber.d("isSearchCompleted $isSearchCompleted")
                if (isSearchCompleted) presenterScope.cancel()
            }
        }
    }

    override fun clearUiMessage(id: Long) {
        TODO("Not yet implemented")
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