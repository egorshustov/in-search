package com.egorshustov.vpoiske.core.model.data

import com.egorshustov.vpoiske.core.common.utils.UnixMillis

data class User(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val gender: Gender?,
    val birthDate: String,
    val city: City?,
    val country: Country?,
    val homeTown: String,
    val photosInfo: UserPhotosInfo,
    val mobilePhone: String,
    val homePhone: String,
    val relation: Relation?,
    val lastSeen: UserLastSeen?,
    val counters: UserCounters?,
    val permissions: UserPermissions,
    // custom fields:
    var searchId: Long?,
    var foundTime: UnixMillis?
)