package com.egorshustov.vpoiske.feature.params

import com.egorshustov.vpoiske.core.model.data.City
import com.egorshustov.vpoiske.core.model.data.Country
import com.egorshustov.vpoiske.core.model.data.Gender
import com.egorshustov.vpoiske.core.model.data.Relation

internal sealed interface ParamsEvent {

    object OnAuthRequested : ParamsEvent

    object RequestCountries : ParamsEvent

    object OnClickResetParamsToDefault : ParamsEvent

    data class OnSelectCountry(val country: Country?) : ParamsEvent

    data class OnSelectCity(val city: City?) : ParamsEvent

    data class OnSelectGender(val gender: Gender) : ParamsEvent

    data class OnSelectAgeFrom(val ageFrom: Int?) : ParamsEvent

    data class OnSelectAgeTo(val ageTo: Int?) : ParamsEvent

    data class OnSelectRelation(val relation: Relation) : ParamsEvent

    data class OnChangeWithPhoneOnly(val withPhoneOnly: Boolean) : ParamsEvent

    data class OnChangeFoundUsersLimit(val foundUsersLimit: Int) : ParamsEvent

    data class OnChangeDaysInterval(val daysInterval: Int) : ParamsEvent

    data class OnChangeNeedToSetFriendsRange(val needToSetFriendsRange: Boolean) : ParamsEvent

    data class OnChangeSelectedFriendsRange(
        val friendsMinCount: Int, val friendsMaxCount: Int
    ) : ParamsEvent

    data class OnChangeSelectedFollowersRange(
        val followersMinCount: Int, val followersMaxCount: Int
    ) : ParamsEvent

    object OnClickStartSearch : ParamsEvent
}