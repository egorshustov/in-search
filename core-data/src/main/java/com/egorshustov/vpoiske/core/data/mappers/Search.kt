package com.egorshustov.vpoiske.core.data.mappers

import com.egorshustov.vpoiske.core.database.model.SearchEntity
import com.egorshustov.vpoiske.core.model.data.Search

fun Search.asEntity() = SearchEntity(
    countryId = country.id,
    countryTitle = country.title,
    cityId = city.id,
    cityTitle = city.title,
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
    startUnixSeconds = startTime.count
)