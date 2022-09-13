package com.egorshustov.vpoiske.feature.params

import androidx.compose.runtime.Immutable
import com.egorshustov.vpoiske.core.model.data.City
import com.egorshustov.vpoiske.core.model.data.Country
import com.egorshustov.vpoiske.core.model.data.Gender
import com.egorshustov.vpoiske.core.model.data.Relation
import com.egorshustov.vpoiske.core.ui.api.UiMessage

private const val DEFAULT_SELECTED_AGE_FROM = 18

private const val DEFAULT_WITH_PHONE_ONLY = false
private const val DEFAULT_FOUND_USERS_LIMIT = 100
private const val DEFAULT_DAYS_INTERVAL = 3

private const val DEFAULT_NEED_TO_SET_FRIENDS_RANGE = true
private const val DEFAULT_SELECTED_FRIENDS_MIN_COUNT = 50
private const val DEFAULT_SELECTED_FRIENDS_MAX_COUNT = 250

private const val DEFAULT_SELECTED_FOLLOWERS_MIN_COUNT = 0
private const val DEFAULT_SELECTED_FOLLOWERS_MAX_COUNT = 150

@Immutable
internal data class ParamsState(
    val searchState: SearchState = SearchState(),
    val authState: AuthState = AuthState(),
    val countriesState: CountriesState = CountriesState(),
    val citiesState: CitiesState = CitiesState(),
    val genderState: GenderState = GenderState(),
    val ageRangeState: AgeRangeState = AgeRangeState(),
    val relationState: RelationState = RelationState(),
    val extraOptionsState: ExtraOptionsState = ExtraOptionsState(),
    val friendsRangeState: FriendsRangeState = FriendsRangeState(),
    val followersRangeState: FollowersRangeState = FollowersRangeState(),
    val isLoading: Boolean = false,
    val message: UiMessage? = null
)

@Immutable
internal data class SearchState(
    val searchId: Long? = null
)

@Immutable
internal data class AuthState(
    val isAuthRequired: Boolean = false
)

@Immutable
internal data class CountriesState(
    val countries: List<Country> = emptyList(),
    val selectedCountry: Country? = null
)

@Immutable
internal data class CitiesState(
    val cities: List<City> = emptyList(),
    val selectedCity: City? = null
)

@Immutable
internal data class GenderState(
    val selectedGender: Gender = Gender.NOT_DEFINED
)

@Immutable
internal data class AgeRangeState(
    val commonAgeRange: IntRange = 18..50,
    val selectedAgeFrom: Int = DEFAULT_SELECTED_AGE_FROM,
    val selectedAgeTo: Int? = null
)

@Immutable
internal data class RelationState(
    val selectedRelation: Relation = Relation.NOT_DEFINED
)

@Immutable
internal data class ExtraOptionsState(
    val withPhoneOnly: Boolean = DEFAULT_WITH_PHONE_ONLY,
    val foundUsersLimit: Int = DEFAULT_FOUND_USERS_LIMIT,
    val daysInterval: Int = DEFAULT_DAYS_INTERVAL
)

@Immutable
internal data class FriendsRangeState(
    val needToSetFriendsRange: Boolean = DEFAULT_NEED_TO_SET_FRIENDS_RANGE,
    val selectedFriendsMinCount: Int = DEFAULT_SELECTED_FRIENDS_MIN_COUNT,
    val selectedFriendsMaxCount: Int = DEFAULT_SELECTED_FRIENDS_MAX_COUNT
)

@Immutable
internal data class FollowersRangeState(
    val selectedFollowersMinCount: Int = DEFAULT_SELECTED_FOLLOWERS_MIN_COUNT,
    val selectedFollowersMaxCount: Int = DEFAULT_SELECTED_FOLLOWERS_MAX_COUNT
)

internal val ParamsState.areSelectionParamsDefault
    get() = countriesState.selectedCountry == null
            && citiesState.selectedCity == null
            && genderState.selectedGender == Gender.NOT_DEFINED
            && ageRangeState.selectedAgeFrom == DEFAULT_SELECTED_AGE_FROM
            && ageRangeState.selectedAgeTo == null
            && relationState.selectedRelation == Relation.NOT_DEFINED
            && extraOptionsState.withPhoneOnly == DEFAULT_WITH_PHONE_ONLY
            && extraOptionsState.foundUsersLimit == DEFAULT_FOUND_USERS_LIMIT
            && extraOptionsState.daysInterval == DEFAULT_DAYS_INTERVAL
            && friendsRangeState.needToSetFriendsRange == DEFAULT_NEED_TO_SET_FRIENDS_RANGE
            && friendsRangeState.selectedFriendsMinCount == DEFAULT_SELECTED_FRIENDS_MIN_COUNT
            && friendsRangeState.selectedFriendsMaxCount == DEFAULT_SELECTED_FRIENDS_MAX_COUNT
            && followersRangeState.selectedFollowersMinCount == DEFAULT_SELECTED_FOLLOWERS_MIN_COUNT
            && followersRangeState.selectedFollowersMaxCount == DEFAULT_SELECTED_FOLLOWERS_MAX_COUNT

internal val ParamsState.isNewSearchCanBeStarted
    get() = !authState.isAuthRequired
            && countriesState.selectedCountry != null
            && citiesState.selectedCity != null