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
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import timber.log.Timber

@OptIn(FlowPreview::class)
internal class ProcessSearchPresenterImpl @AssistedInject constructor(
    @Assisted("searchId") private val searchId: Long,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
    getAccessTokenUseCase: GetAccessTokenUseCase,
    private val getSearchUseCase: GetSearchUseCase,
    getUsersCountUseCase: GetUsersCountUseCase,
    private val searchUsersUseCase: SearchUsersUseCase,
    private val saveUsersUseCase: SaveUsersUseCase
) : ProcessSearchPresenter {

    // This exception handler allows to catch non-cancellation exceptions
    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        Timber.d("exceptionHandler thread $currentThreadName")
        Timber.d("Caught exceptionHandler $exception")
    }

    private val presenterScope = CoroutineScope(ioDispatcher + exceptionHandler)

    private val accessTokenFlow: StateFlow<String> = getAccessTokenUseCase(Unit)
        .mapNotNull { it.data }
        .log("accessTokenFlow")
        .stateIn(
            scope = presenterScope,
            started = SharingStarted.Lazily,
            initialValue = ""
        )

    private val foundUsersCountFlow: StateFlow<Int> = getUsersCountUseCase(
        GetUsersCountUseCaseParams(searchId)
    ).mapNotNull { it.data }
        .log("foundUsersCountFlow")
        .stateIn(
            scope = presenterScope,
            started = SharingStarted.WhileSubscribed(5000),
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
        uiMessageManager.message,
    ) { foundUsersCount, message ->
        ProcessSearchState(foundUsersCount, message)
    }.distinctUntilChanged()
        .log("ProcessSearchState")
        .stateIn(
            scope = presenterScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ProcessSearchState.Empty
        )

    init {
        collectIsSearchCompleted()
    }

    override suspend fun startSearch(): Job = presenterScope.launch {
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
        while (true) {
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

    private suspend fun searchUsers(
        search: Search,
        birthDay: Int,
        birthMonth: Int
    ): Job = CoroutineScope(presenterScope.coroutineContext).launch {
        // todo check if: presenterScope.launch { gonna work
        accessTokenFlow
            .filterNot { accessToken -> accessToken.isBlank() }
            .getUsersWithSearchRequest(
                search = search,
                birthDay = birthDay,
                birthMonth = birthMonth
            )
            .filterUsersObtainedWithSearchRequest(search)
            .mapResult { filteredSearchUsers ->
                if (search.isFriendsLimitSet) {
                    // TODO sendGetUserRequest for every filteredSearchUsersList user
                } else {
                    filteredSearchUsers.apply {
                        val time = currentTime
                        forEach {
                            it.searchId = search.id
                            it.foundTime = time
                        }
                    }
                    saveUsersUseCase(SaveUsersUseCaseParams(filteredSearchUsers)).also {
                        Timber.d("Saved users count: ${it.data?.size}")
                    }
                }
            }
            .ignoreResultData()
            .onEach {
                Timber.d("onEach $it")
                if (it.succeeded) currentCoroutineContext().cancel()
            }.launchIn(this)

        Timber.d("Search users return")
    }

    private fun Flow<String>.getUsersWithSearchRequest(
        search: Search,
        birthDay: Int,
        birthMonth: Int
    ): Flow<Result<List<User>>> = flatMapMerge { accessToken ->
        searchUsersUseCase(
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
        ).also {
            Timber.d("Let's wait for a ${pauseDelay.count} millis to prevent API flood block")
            delay(pauseDelay.toDuration())
        }
    }

    private fun Flow<Result<List<User>>>.filterUsersObtainedWithSearchRequest(search: Search): Flow<Result<List<User>>> =
        mapResult { users ->
            Timber.d(
                "Filter users obtained with search request\n" +
                        "Search users count initial: ${users.size}"
            )
            users.filter { user ->
                val isNotClosed = user.permissions.isClosed == false
                val isFollowersCountAcceptable =
                    user.counters?.followers in search.followersMinCount..search.followersMaxCount
                val isInDaysInterval =
                    search.startTime - (user.lastSeen?.time ?: UnixSeconds.Zero) <
                            UnixSeconds(search.daysInterval * SECONDS_IN_DAY)
                val phoneCheckPassed = if (search.withPhoneOnly) user.hasValidPhone else true

                isNotClosed && isFollowersCountAcceptable && isInDaysInterval && phoneCheckPassed
            }.also {
                Timber.d("Search users count filtered: ${it.size}")
            }
        }

    private fun collectIsSearchCompleted() {
        presenterScope.launch {
            isSearchCompletedFlow.collectLatest { isSearchCompleted ->
                Timber.d("isSearchCompleted $isSearchCompleted")
                if (isSearchCompleted) cancel()
            }
        }
    }

    override fun clearUiMessage(id: Long) {
        TODO("Not yet implemented")
    }

    @AssistedFactory
    interface Factory {

        fun create(@Assisted("searchId") searchId: Long): ProcessSearchPresenterImpl
    }

    private companion object {

        private val pauseDelay: Millis = Millis(500)
    }
}