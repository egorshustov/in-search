package com.egorshustov.vpoiske.core.model.data

data class User(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val sex: Int?,
    val birthDate: String,
    val city: City?,
    val country: Country?,
    val homeTown: String,
    val photosInfo: UserPhotosInfo,
    val mobilePhone: String,
    val homePhone: String,
    val relation: Int?,
    val lastSeen: UserLastSeen?,
    val counters: UserCounters?,
    val usersPermissions: UserPermissions,
    // custom fields:
    var searchId: Long?,
    var foundUnixMillis: Long?
)