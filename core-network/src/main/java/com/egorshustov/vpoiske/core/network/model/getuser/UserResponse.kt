package com.egorshustov.vpoiske.core.network.model.getuser

import com.egorshustov.vpoiske.core.common.utils.NO_VALUE_L
import com.egorshustov.vpoiske.core.common.utils.UrlString
import com.egorshustov.vpoiske.core.model.data.*
import com.egorshustov.vpoiske.core.network.model.getcities.CityResponse
import com.egorshustov.vpoiske.core.network.model.getcities.asExternalModel
import com.egorshustov.vpoiske.core.network.model.getcountries.CountryResponse
import com.egorshustov.vpoiske.core.network.model.getcountries.asExternalModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(

    @SerialName("id")
    val id: Long? = null,

    @SerialName("first_name")
    val firstName: String? = null,

    @SerialName("last_name")
    val lastName: String? = null,

    @SerialName("is_closed")
    val isClosed: Boolean? = null,

    @SerialName("can_access_closed")
    val canAccessClosed: Boolean? = null,

    @SerialName("sex")
    val genderId: Int? = null,

    @SerialName("bdate")
    val birthDate: String? = null,

    @SerialName("city")
    val city: CityResponse? = null,

    @SerialName("country")
    val country: CountryResponse? = null,

    @SerialName("home_town")
    val homeTown: String? = null,

    @SerialName("photo_id")
    val photoId: String?,

    @SerialName("photo_50")
    val photo50: UrlString? = null,

    @SerialName("photo_max")
    val photoMax: UrlString? = null,

    @SerialName("photo_max_orig")
    val photoMaxOrig: UrlString? = null,

    @SerialName("can_write_private_message")
    val canWritePrivateMessage: Int? = null,

    @SerialName("can_send_friend_request")
    val canSendFriendRequest: Int? = null,

    @SerialName("mobile_phone")
    val mobilePhone: String? = null,

    @SerialName("home_phone")
    val homePhone: String? = null,

    @SerialName("relation")
    val relationId: Int? = null,

    @SerialName("counters")
    val counters: UserCountersResponse? = null
)

fun UserResponse.asExternalModel() = User(
    id = id ?: NO_VALUE_L,
    firstName = firstName.orEmpty(),
    lastName = lastName.orEmpty(),
    gender = Gender.getByIdOrNull(genderId),
    birthDate = birthDate.orEmpty(),
    city = city.asExternalModel(),
    country = country.asExternalModel(),
    homeTown = homeTown.orEmpty(),
    photosInfo = getUserPhotosInfo(),
    mobilePhone = mobilePhone.orEmpty(),
    homePhone = homePhone.orEmpty(),
    relation = Relation.getByIdOrNull(relationId),
    lastSeen = null,
    counters = counters.asExternalModel(),
    permissions = getUserPermissions(),
    searchId = null,
    foundTime = null
)

fun UserResponse.getUserPhotosInfo() = UserPhotosInfo(
    photoId = photoId.orEmpty(),
    photo50 = photo50.orEmpty(),
    photoMax = photoMax.orEmpty(),
    photoMaxOrig = photoMaxOrig.orEmpty()
)

fun UserResponse.getUserPermissions() = UserPermissions(
    isClosed = isClosed,
    canAccessClosed = canAccessClosed,
    canWritePrivateMessage = canWritePrivateMessage?.let { it == 1 },
    canSendFriendRequest = canSendFriendRequest?.let { it == 1 }
)