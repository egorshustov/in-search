package com.egorshustov.vpoiske.core.model.data

import com.egorshustov.vpoiske.core.common.utils.UnixSeconds

data class Search(
    val countryId: Int,
    val countryTitle: String,
    val cityId: Int,
    val cityTitle: String,
    val homeTown: String?,
    val gender: Gender,
    val ageFrom: Int?,
    val ageTo: Int?,
    val relation: Relation,
    val withPhoneOnly: Boolean,
    val foundUsersLimit: Int,
    val daysInterval: Int,
    val friendsMinCount: Int?,
    val friendsMaxCount: Int?,
    val followersMinCount: Int,
    val followersMaxCount: Int,
    val startTime: UnixSeconds
)