package com.egorshustov.vpoiske.core.data.mappers

import com.egorshustov.vpoiske.core.common.utils.NO_VALUE_L
import com.egorshustov.vpoiske.core.database.model.UserCountersEmbedded
import com.egorshustov.vpoiske.core.database.model.UserEntity
import com.egorshustov.vpoiske.core.database.model.UserPermissionsEmbedded
import com.egorshustov.vpoiske.core.database.model.UserPhotosInfoEmbedded
import com.egorshustov.vpoiske.core.model.data.User
import com.egorshustov.vpoiske.core.model.data.UserCounters
import com.egorshustov.vpoiske.core.model.data.UserPermissions
import com.egorshustov.vpoiske.core.model.data.UserPhotosInfo

internal fun User.asEntity() = UserEntity(
    id = id,
    firstName = firstName,
    lastName = lastName,
    gender = gender,
    birthDate = birthDate,
    city = city.asEmbedded(),
    country = country.asEmbedded(),
    homeTown = homeTown,
    photosInfo = photosInfo.asEmbedded(),
    mobilePhone = mobilePhone,
    homePhone = homePhone,
    relation = relation,
    counters = counters.asEmbedded(),
    permissions = permissions.asEmbedded(),
    searchId = searchId ?: NO_VALUE_L,
    foundUnixMillis = foundTime?.count ?: NO_VALUE_L
)

internal fun List<User>.asEntityList() = map { it.asEntity() }

internal fun UserPhotosInfo.asEmbedded() = UserPhotosInfoEmbedded(
    photoId = photoId,
    photo50 = photo50,
    photoMax = photoMax,
    photoMaxOrig = photoMaxOrig
)

internal fun UserCounters?.asEmbedded() = UserCountersEmbedded(
    albums = this?.albums,
    videos = this?.videos,
    audios = this?.audios,
    photos = this?.photos,
    notes = this?.notes,
    gifts = this?.gifts,
    articles = this?.articles,
    friends = this?.friends,
    groups = this?.groups,
    mutualFriends = this?.mutualFriends,
    userPhotos = this?.userPhotos,
    userVideos = this?.userVideos,
    followers = this?.followers,
    clipsFollowers = this?.clipsFollowers,
    subscriptions = this?.subscriptions,
    pages = this?.pages
)

internal fun UserPermissions.asEmbedded() = UserPermissionsEmbedded(
    isClosed = isClosed,
    canAccessClosed = canAccessClosed,
    canWritePrivateMessage = canWritePrivateMessage,
    canSendFriendRequest = canSendFriendRequest
)