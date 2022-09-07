package com.egorshustov.vpoiske.feature.params

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.egorshustov.vpoiske.core.common.model.Result
import com.egorshustov.vpoiske.core.common.utils.currentTime
import com.egorshustov.vpoiske.core.common.utils.toSeconds
import com.egorshustov.vpoiske.core.domain.city.GetCitiesUseCase
import com.egorshustov.vpoiske.core.domain.city.GetCitiesUseCaseParams
import com.egorshustov.vpoiske.core.domain.country.GetCountriesStreamUseCase
import com.egorshustov.vpoiske.core.domain.country.RequestCountriesUseCase
import com.egorshustov.vpoiske.core.domain.country.RequestCountriesUseCaseParams
import com.egorshustov.vpoiske.core.domain.search.GetLastSearchUseCase
import com.egorshustov.vpoiske.core.domain.search.SaveSearchUseCase
import com.egorshustov.vpoiske.core.domain.search.SaveSearchUseCaseParams
import com.egorshustov.vpoiske.core.domain.token.GetAccessTokenUseCase
import com.egorshustov.vpoiske.core.model.data.*
import com.egorshustov.vpoiske.core.model.data.requestsparams.GetCitiesRequestParams
import com.egorshustov.vpoiske.core.model.data.requestsparams.VkCommonRequestParams
import com.egorshustov.vpoiske.core.ui.api.UiMessageManager
import com.egorshustov.vpoiske.core.ui.util.ObservableLoadingCounter
import com.egorshustov.vpoiske.core.ui.util.WhileSubscribed
import com.egorshustov.vpoiske.core.ui.util.unwrapResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ParamsViewModel @Inject constructor(
    getAccessTokenUseCase: GetAccessTokenUseCase,
    private val requestCountriesUseCase: RequestCountriesUseCase,
    private val getLastSearchUseCase: GetLastSearchUseCase,
    private val getCountriesStreamUseCase: GetCountriesStreamUseCase,
    private val getCitiesUseCase: GetCitiesUseCase,
    private val saveSearchUseCase: SaveSearchUseCase
) : ViewModel() {

    private val loadingState = ObservableLoadingCounter()
    private val uiMessageManager = UiMessageManager()

    private val accessTokenFlow: StateFlow<String> = getAccessTokenUseCase(Unit)
        .unwrapResult(loadingState, uiMessageManager)
        .onEach { accessToken ->
            if (accessToken.isEmpty()) {
                _state.update { it.copy(authState = it.authState.copy(isAuthRequired = true)) }
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = WhileSubscribed,
            initialValue = ""
        )

    private val _state: MutableStateFlow<ParamsState> = MutableStateFlow(ParamsState())
    val state: StateFlow<ParamsState> = _state.asStateFlow()

    init {
        collectCountriesStream()
        collectLoadingState()
        collectUiMessageManager()
        onTriggerEvent(ParamsEvent.RequestCountries)
        onTriggerEvent(ParamsEvent.GetLastSearch)
    }

    fun onTriggerEvent(event: ParamsEvent) {
        when (event) {
            ParamsEvent.OnAuthRequested -> onAuthRequested()
            ParamsEvent.OnSearchProcessInitiated -> onSearchProcessInitiated()
            ParamsEvent.RequestCountries -> viewModelScope.launch { requestCountries() }
            ParamsEvent.GetLastSearch -> getLastSearch()
            ParamsEvent.OnClickResetParamsToDefault -> onClickResetParamsToDefault()
            is ParamsEvent.OnSelectCountry -> onSelectCountry(event.country)
            is ParamsEvent.OnSelectCity -> onSelectCity(event.city)
            is ParamsEvent.OnSelectGender -> onSelectGender(event.gender)
            is ParamsEvent.OnSelectAgeFrom -> onSelectAgeFrom(event.ageFrom)
            is ParamsEvent.OnSelectAgeTo -> onSelectAgeTo(event.ageTo)
            is ParamsEvent.OnSelectRelation -> onSelectRelation(event.relation)
            is ParamsEvent.OnChangeWithPhoneOnly -> onChangeWithPhoneOnly(event.withPhoneOnly)
            is ParamsEvent.OnChangeFoundUsersLimit -> onChangeFoundUsersLimit(event.foundUsersLimit)
            is ParamsEvent.OnChangeDaysInterval -> onChangeDaysInterval(event.daysInterval)
            is ParamsEvent.OnChangeNeedToSetFriendsRange -> onChangeNeedToSetFriendsRange(event.needToSetFriendsRange)
            is ParamsEvent.OnChangeSelectedFriendsRange -> onChangeSelectedFriendsRange(
                event.friendsMinCount,
                event.friendsMaxCount
            )
            is ParamsEvent.OnChangeSelectedFollowersRange -> onChangeSelectedFollowersRange(
                event.followersMinCount,
                event.followersMaxCount
            )
            ParamsEvent.OnClickStartSearch -> onClickStartSearch()
            is ParamsEvent.ClearUiMessage -> onClearUiMessage(event.uiMessageId)
        }
    }

    private fun onAuthRequested() {
        _state.update {
            it.copy(authState = it.authState.copy(isAuthRequired = false))
        }
    }

    private fun onSearchProcessInitiated() {
        _state.update {
            it.copy(searchState = it.searchState.copy(searchId = null))
        }
    }

    private suspend fun requestCountries() {
        val accessToken = accessTokenFlow.filterNot { it.isEmpty() }.first()

        requestCountriesUseCase(
            RequestCountriesUseCaseParams(
                VkCommonRequestParams(accessToken)
            )
        ).unwrapResult(loadingState, uiMessageManager)
            .launchIn(viewModelScope)
    }

    private fun getLastSearch() {
        viewModelScope.launch {
            val lastSearchResult = getLastSearchUseCase(Unit)
            if (lastSearchResult is Result.Success) {
                val lastSearch = lastSearchResult.data
                _state.updateAndGetParamsStateFromSearchModel(lastSearch)
                getCities(lastSearch.country.id)
            }
        }
    }

    private fun MutableStateFlow<ParamsState>.updateAndGetParamsStateFromSearchModel(search: Search): ParamsState =
        updateAndGet {
            it.copy(
                countriesState = it.countriesState.copy(selectedCountry = search.country),
                citiesState = it.citiesState.copy(selectedCity = search.city),
                genderState = it.genderState.copy(selectedGender = search.gender),
                ageRangeState = it.ageRangeState.copy(
                    selectedAgeFrom = search.ageFrom,
                    selectedAgeTo = search.ageTo
                ),
                relationState = it.relationState.copy(selectedRelation = search.relation),
                extraOptionsState = it.extraOptionsState.copy(
                    withPhoneOnly = search.withPhoneOnly,
                    foundUsersLimit = search.foundUsersLimit,
                    daysInterval = search.daysInterval
                ),
                friendsRangeState = it.friendsRangeState.copy(
                    needToSetFriendsRange = search.friendsMinCount != null || search.friendsMaxCount != null,
                    selectedFriendsMinCount = search.friendsMinCount
                        ?: it.friendsRangeState.selectedFriendsMinCount,
                    selectedFriendsMaxCount = search.friendsMaxCount
                        ?: it.friendsRangeState.selectedFriendsMaxCount
                ),
                followersRangeState = it.followersRangeState.copy(
                    selectedFollowersMinCount = search.followersMinCount,
                    selectedFollowersMaxCount = search.followersMaxCount,
                )
            )
        }

    private fun onClickResetParamsToDefault() {
        _state.update { ParamsState(countriesState = CountriesState(it.countriesState.countries)) }
    }

    private fun onSelectCountry(country: Country?) {
        if (country != state.value.countriesState.selectedCountry) {
            _state.update {
                it.copy(
                    countriesState = it.countriesState.copy(selectedCountry = country),
                    citiesState = it.citiesState.copy(selectedCity = null)
                )
            }
            if (country == null) {
                _state.update { it.copy(citiesState = it.citiesState.copy(cities = emptyList())) }
            } else {
                viewModelScope.launch { getCities(country.id) }
            }
        }
    }

    private suspend fun getCities(countryId: Int) {
        val accessToken = accessTokenFlow.filterNot { it.isEmpty() }.first()

        getCitiesUseCase(
            GetCitiesUseCaseParams(
                GetCitiesRequestParams(countryId),
                VkCommonRequestParams(accessToken)
            )
        ).unwrapResult(loadingState, uiMessageManager)
            .onEach { cities ->
                _state.update { it.copy(citiesState = it.citiesState.copy(cities = cities)) }
            }.launchIn(viewModelScope)
    }

    private fun onSelectCity(city: City?) {
        _state.update { it.copy(citiesState = it.citiesState.copy(selectedCity = city)) }
    }

    private fun onSelectGender(gender: Gender) {
        _state.update { it.copy(genderState = it.genderState.copy(selectedGender = gender)) }
    }

    private fun onSelectAgeFrom(ageFrom: Int?) {
        _state.update { it.copy(ageRangeState = it.ageRangeState.copy(selectedAgeFrom = ageFrom)) }

        val ageTo = state.value.ageRangeState.selectedAgeTo
        if (ageFrom == null || ageTo == null) return

        if (ageFrom > ageTo) {
            _state.update { it.copy(ageRangeState = it.ageRangeState.copy(selectedAgeTo = ageFrom)) }
        }
    }

    private fun onSelectAgeTo(ageTo: Int?) {
        _state.update { it.copy(ageRangeState = it.ageRangeState.copy(selectedAgeTo = ageTo)) }

        val ageFrom = state.value.ageRangeState.selectedAgeFrom
        if (ageFrom == null || ageTo == null) return

        if (ageFrom > ageTo) {
            _state.update { it.copy(ageRangeState = it.ageRangeState.copy(selectedAgeFrom = ageTo)) }
        }
    }

    private fun onSelectRelation(relation: Relation) {
        _state.update { it.copy(relationState = it.relationState.copy(selectedRelation = relation)) }
    }

    private fun onChangeWithPhoneOnly(withPhoneOnly: Boolean) {
        _state.update {
            it.copy(extraOptionsState = it.extraOptionsState.copy(withPhoneOnly = withPhoneOnly))
        }
    }

    private fun onChangeFoundUsersLimit(foundUsersLimit: Int) {
        _state.update { it.copy(extraOptionsState = it.extraOptionsState.copy(foundUsersLimit = foundUsersLimit)) }
    }

    private fun onChangeDaysInterval(daysInterval: Int) {
        _state.update { it.copy(extraOptionsState = it.extraOptionsState.copy(daysInterval = daysInterval)) }
    }

    private fun onChangeNeedToSetFriendsRange(needToSetFriendsRange: Boolean) {
        _state.update { it.copy(friendsRangeState = it.friendsRangeState.copy(needToSetFriendsRange = needToSetFriendsRange)) }
    }

    private fun onChangeSelectedFriendsRange(friendsMinCount: Int, friendsMaxCount: Int) {
        _state.update {
            it.copy(
                friendsRangeState = it.friendsRangeState.copy(
                    selectedFriendsMinCount = friendsMinCount,
                    selectedFriendsMaxCount = friendsMaxCount
                )
            )
        }
    }

    private fun onChangeSelectedFollowersRange(followersMinCount: Int, followersMaxCount: Int) {
        _state.update {
            it.copy(
                followersRangeState = it.followersRangeState.copy(
                    selectedFollowersMinCount = followersMinCount,
                    selectedFollowersMaxCount = followersMaxCount
                )
            )
        }
    }

    private fun onClickStartSearch() {
        val search = createSearchModelFromParamsState(state.value) ?: return
        viewModelScope.launch {
            saveSearchUseCase(SaveSearchUseCaseParams(search))
                .unwrapResult(loadingState, uiMessageManager)
                ?.also { searchId ->
                    _state.update { it.copy(searchState = it.searchState.copy(searchId = searchId)) }
                }
        }
    }

    private fun createSearchModelFromParamsState(state: ParamsState): Search? {
        val country = state.countriesState.selectedCountry
        val city = state.citiesState.selectedCity
        if (country == null || city == null) return null

        val homeTown: String? = null // todo: add homeTown input functionality
        val gender = state.genderState.selectedGender
        val ageFrom = state.ageRangeState.selectedAgeFrom
        val ageTo = state.ageRangeState.selectedAgeTo
        val relation = state.relationState.selectedRelation
        val withPhoneOnly = state.extraOptionsState.withPhoneOnly
        val foundUsersLimit = state.extraOptionsState.foundUsersLimit
        val daysInterval = state.extraOptionsState.daysInterval
        val friendsMinCount = state.friendsRangeState.selectedFriendsMinCount
            .takeIf { state.friendsRangeState.needToSetFriendsRange }
        val friendsMaxCount = state.friendsRangeState.selectedFriendsMaxCount
            .takeIf { state.friendsRangeState.needToSetFriendsRange }
        val followersMinCount = state.followersRangeState.selectedFollowersMinCount
        val followersMaxCount = state.followersRangeState.selectedFollowersMaxCount
        val startTime = currentTime.toSeconds()

        return Search(
            country = country,
            city = city,
            homeTown = homeTown,
            gender = gender,
            ageFrom = ageFrom,
            ageTo = ageTo,
            relation = relation,
            withPhoneOnly = withPhoneOnly,
            foundUsersLimit = foundUsersLimit,
            daysInterval = daysInterval,
            friendsMinCount = friendsMinCount,
            friendsMaxCount = friendsMaxCount,
            followersMinCount = followersMinCount,
            followersMaxCount = followersMaxCount,
            startTime = startTime
        )
    }

    private fun onClearUiMessage(uiMessageId: Long) {
        viewModelScope.launch {
            uiMessageManager.clearMessage(uiMessageId)
        }
    }

    private fun collectCountriesStream() {
        getCountriesStreamUseCase(Unit)
            .unwrapResult(loadingState, uiMessageManager)
            .onEach { countries ->
                if (countries.isNotEmpty()) {
                    _state.update { it.copy(countriesState = it.countriesState.copy(countries = countries)) }
                }
            }.launchIn(viewModelScope)
    }

    private fun collectLoadingState() {
        loadingState.flow.onEach { isLoading ->
            _state.update { it.copy(isLoading = isLoading) }
        }.launchIn(viewModelScope)
    }

    private fun collectUiMessageManager() {
        uiMessageManager.message.onEach { message ->
            _state.update { it.copy(message = message) }
        }.launchIn(viewModelScope)
    }
}