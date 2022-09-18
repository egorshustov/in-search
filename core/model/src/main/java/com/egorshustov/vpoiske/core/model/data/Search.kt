package com.egorshustov.vpoiske.core.model.data

import com.egorshustov.vpoiske.core.common.utils.UnixSeconds

data class Search(
    val country: Country,
    val city: City,
    val homeTown: String?,
    val gender: Gender,
    val ageFrom: Int,
    val ageTo: Int?,
    val relation: Relation,
    val withPhoneOnly: Boolean,
    val foundUsersLimit: Int,
    val daysInterval: Int,
    val friendsMinCount: Int?,
    val friendsMaxCount: Int?,
    val followersMinCount: Int,
    val followersMaxCount: Int,
    val startTime: UnixSeconds,
    val id: Long? = null
)

val Search.isFriendsLimitSet: Boolean
    get() = friendsMinCount != null || friendsMaxCount != null

val mockSearch = Search( // todo: move to tests directory later
    country = Country(1, "Russia"),
    city = City(1, "Saint Petersburg", "", ""),
    homeTown = "Kurgan",
    gender = Gender.FEMALE,
    ageFrom = 18,
    ageTo = 40,
    relation = Relation.NOT_MARRIED,
    withPhoneOnly = false,
    foundUsersLimit = 100,
    daysInterval = 3,
    friendsMinCount = 50,
    friendsMaxCount = 250,
    followersMinCount = 0,
    followersMaxCount = 200,
    startTime = UnixSeconds(1663486092),
    id = 1
)