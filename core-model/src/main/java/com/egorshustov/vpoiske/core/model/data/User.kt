package com.egorshustov.vpoiske.core.model.data

import com.egorshustov.vpoiske.core.common.utils.UnixMillis
import com.egorshustov.vpoiske.core.common.utils.extractDigits

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

private const val MOBILE_PHONE_MIN_LENGTH = 10
private const val HOME_PHONE_MIN_LENGTH = 6

val User.hasValidPhone: Boolean
    get() = isMobilePhoneValid || isHomePhoneValid

private val User.isMobilePhoneValid: Boolean
    get() = when {
        mobilePhone.isBlank() -> false
        mobilePhone.extractDigits().length < MOBILE_PHONE_MIN_LENGTH -> false
        // maybe should add additional logical conditions here (depending on the final output)
        else -> true
    }


private val User.isHomePhoneValid: Boolean
    get() = when {
        homePhone.isBlank() -> false
        homePhone.extractDigits().length < HOME_PHONE_MIN_LENGTH -> false
        // maybe should add additional logical conditions here (depending on the final output)
        else -> true
    }

val mockUser = User( // todo: move to tests directory later
    id = 0,
    firstName = "firstName",
    lastName = "lastName",
    gender = Gender.MALE,
    birthDate = "22.11",
    city = null,
    country = null,
    homeTown = "",
    photosInfo = UserPhotosInfo(
        photoId = "",
        photo50 = "",
        photoMax = "",
        photoMaxOrig = ""
    ),
    mobilePhone = "",
    homePhone = "",
    relation = Relation.NOT_DEFINED,
    lastSeen = null,
    counters = null,
    permissions = UserPermissions(
        isClosed = true,
        canAccessClosed = true,
        canWritePrivateMessage = true,
        canSendFriendRequest = true
    ),
    searchId = -1,
    foundTime = UnixMillis(0)
)