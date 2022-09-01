package com.egorshustov.vpoiske.feature.params

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.egorshustov.vpoiske.core.common.model.Result
import com.egorshustov.vpoiske.core.common.model.data
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

    private val _state: MutableState<ParamsState> = mutableStateOf(ParamsState())
    val state: State<ParamsState> = _state

    private val accessTokenFlow: StateFlow<String> = getAccessTokenUseCase(Unit)
        .mapNotNull {
            if (it.data?.isEmpty() == true) {
                _state.value = state.value.copy(
                    authState = state.value.authState.copy(isAuthRequired = true)
                )
            }
            it.data
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = ""
        )

    init {
        subscribeCountriesStream()
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
        }
    }

    private fun onAuthRequested() {
        _state.value = state.value.copy(
            authState = state.value.authState.copy(isAuthRequired = false)
        )
    }

    private fun onSearchProcessInitiated() {
        _state.value = state.value.copy(
            searchState = state.value.searchState.copy(searchId = null)
        )
    }

    private suspend fun requestCountries() {
        val accessToken = accessTokenFlow.filterNot { it.isEmpty() }.first()

        requestCountriesUseCase(
            RequestCountriesUseCaseParams(
                VkCommonRequestParams(accessToken)
            )
        ).onEach { result ->
            when (result) {
                Result.Loading -> {}
                is Result.Success -> Unit
                is Result.Error -> {}
            }
        }.launchIn(viewModelScope)
    }

    private fun getLastSearch() {
        viewModelScope.launch {
            val lastSearchResult = getLastSearchUseCase(Unit)
            if (lastSearchResult is Result.Success) {
                val lastSearch = lastSearchResult.data
                _state.value = createParamsStateFromSearchModel(lastSearch)
                getCities(lastSearch.country.id)
            }
        }
    }

    private fun createParamsStateFromSearchModel(search: Search): ParamsState = state.value.copy(
        countriesState = state.value.countriesState.copy(selectedCountry = search.country),
        citiesState = state.value.citiesState.copy(selectedCity = search.city),
        genderState = state.value.genderState.copy(selectedGender = search.gender),
        ageRangeState = state.value.ageRangeState.copy(
            selectedAgeFrom = search.ageFrom,
            selectedAgeTo = search.ageTo
        ),
        relationState = state.value.relationState.copy(selectedRelation = search.relation),
        extraOptionsState = state.value.extraOptionsState.copy(
            withPhoneOnly = search.withPhoneOnly,
            foundUsersLimit = search.foundUsersLimit,
            daysInterval = search.daysInterval
        ),
        friendsRangeState = state.value.friendsRangeState.copy(
            needToSetFriendsRange = search.friendsMinCount != null || search.friendsMaxCount != null,
            selectedFriendsMinCount = search.friendsMinCount
                ?: state.value.friendsRangeState.selectedFriendsMinCount,
            selectedFriendsMaxCount = search.friendsMaxCount
                ?: state.value.friendsRangeState.selectedFriendsMaxCount
        ),
        followersRangeState = state.value.followersRangeState.copy(
            selectedFollowersMinCount = search.followersMinCount,
            selectedFollowersMaxCount = search.followersMaxCount,
        )
    )

    private fun onClickResetParamsToDefault() {
        val savedCountries = state.value.countriesState.countries
        _state.value = ParamsState(countriesState = CountriesState(savedCountries))
    }

    private fun onSelectCountry(country: Country?) {
        if (country != state.value.countriesState.selectedCountry) {
            _state.value = state.value.copy(
                countriesState = state.value.countriesState.copy(selectedCountry = country),
                citiesState = state.value.citiesState.copy(selectedCity = null)
            )
            if (country == null) {
                _state.value = state.value.copy(
                    citiesState = state.value.citiesState.copy(cities = emptyList())
                )
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
        ).onEach { result ->
            when (result) {
                Result.Loading -> {}
                is Result.Success -> _state.value = state.value.copy(
                    citiesState = state.value.citiesState.copy(cities = result.data)
                )
                is Result.Error -> {}
            }
        }.launchIn(viewModelScope)
    }

    private fun onSelectCity(city: City?) {
        _state.value = state.value.copy(
            citiesState = state.value.citiesState.copy(selectedCity = city)
        )
    }

    private fun onSelectGender(gender: Gender) {
        _state.value = state.value.copy(
            genderState = state.value.genderState.copy(selectedGender = gender)
        )
    }

    private fun onSelectAgeFrom(ageFrom: Int?) {
        _state.value = state.value.copy(
            ageRangeState = state.value.ageRangeState.copy(selectedAgeFrom = ageFrom)
        )

        val ageTo = state.value.ageRangeState.selectedAgeTo
        if (ageFrom == null || ageTo == null) return

        if (ageFrom > ageTo) {
            _state.value = state.value.copy(
                ageRangeState = state.value.ageRangeState.copy(selectedAgeTo = ageFrom)
            )
        }
    }

    private fun onSelectAgeTo(ageTo: Int?) {
        _state.value = state.value.copy(
            ageRangeState = state.value.ageRangeState.copy(selectedAgeTo = ageTo)
        )

        val ageFrom = state.value.ageRangeState.selectedAgeFrom
        if (ageFrom == null || ageTo == null) return

        if (ageFrom > ageTo) {
            _state.value = state.value.copy(
                ageRangeState = state.value.ageRangeState.copy(selectedAgeFrom = ageTo)
            )
        }
    }

    private fun onSelectRelation(relation: Relation) {
        _state.value = state.value.copy(
            relationState = state.value.relationState.copy(selectedRelation = relation)
        )
    }

    private fun onChangeWithPhoneOnly(withPhoneOnly: Boolean) {
        _state.value = state.value.copy(
            extraOptionsState = state.value.extraOptionsState.copy(withPhoneOnly = withPhoneOnly)
        )
    }

    private fun onChangeFoundUsersLimit(foundUsersLimit: Int) {
        _state.value = state.value.copy(
            extraOptionsState = state.value.extraOptionsState.copy(foundUsersLimit = foundUsersLimit)
        )
    }

    private fun onChangeDaysInterval(daysInterval: Int) {
        _state.value = state.value.copy(
            extraOptionsState = state.value.extraOptionsState.copy(daysInterval = daysInterval)
        )
    }

    private fun onChangeNeedToSetFriendsRange(needToSetFriendsRange: Boolean) {
        _state.value = state.value.copy(
            friendsRangeState = state.value.friendsRangeState.copy(needToSetFriendsRange = needToSetFriendsRange)
        )
    }

    private fun onChangeSelectedFriendsRange(friendsMinCount: Int, friendsMaxCount: Int) {
        _state.value = state.value.copy(
            friendsRangeState = state.value.friendsRangeState.copy(
                selectedFriendsMinCount = friendsMinCount,
                selectedFriendsMaxCount = friendsMaxCount
            )
        )
    }

    private fun onChangeSelectedFollowersRange(followersMinCount: Int, followersMaxCount: Int) {
        _state.value = state.value.copy(
            followersRangeState = state.value.followersRangeState.copy(
                selectedFollowersMinCount = followersMinCount,
                selectedFollowersMaxCount = followersMaxCount
            )
        )
    }

    private fun onClickStartSearch() {
        createSearchModelFromParamsState(state.value)?.let { search ->
            viewModelScope.launch {
                when (val searchIdResult = saveSearchUseCase(SaveSearchUseCaseParams(search))) {
                    Result.Loading -> {}
                    is Result.Success -> {
                        _state.value = state.value.copy(
                            searchState = state.value.searchState.copy(
                                searchId = searchIdResult.data
                            )
                        )
                    }
                    is Result.Error -> {}
                }
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

    private fun subscribeCountriesStream() {
        getCountriesStreamUseCase(Unit).onEach {
            val countries = it.data
            if (!countries.isNullOrEmpty()) {
                _state.value = state.value.copy(
                    countriesState = state.value.countriesState.copy(countries = countries)
                )
            }
        }.launchIn(viewModelScope)
    }
}