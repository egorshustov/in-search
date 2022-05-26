package com.egorshustov.vpoiske.core.network.model.getuser

import com.egorshustov.vpoiske.core.common.utils.NO_VALUE
import com.egorshustov.vpoiske.core.model.data.User
import com.egorshustov.vpoiske.core.model.data.UserPermissions
import com.egorshustov.vpoiske.core.model.data.UserPhotosInfo
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
    val sex: Int? = null,

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
    val photo50Url: String? = null,

    @SerialName("photo_max")
    val photoMaxUrl: String? = null,

    @SerialName("photo_max_orig")
    val photoMaxOrigUrl: String? = null,

    @SerialName("can_write_private_message")
    val canWritePrivateMessage: Int? = null,

    @SerialName("can_send_friend_request")
    val canSendFriendRequest: Int? = null,

    @SerialName("mobile_phone")
    val mobilePhone: String? = null,

    @SerialName("home_phone")
    val homePhone: String? = null,

    @SerialName("relation")
    val relation: Int? = null,

    @SerialName("counters")
    val counters: UserCountersResponse? = null
)

fun UserResponse.asExternalModel() = User(
    id = id ?: NO_VALUE.toLong(),
    firstName = firstName.orEmpty(),
    lastName = lastName.orEmpty(),
    sex = sex,
    birthDate = birthDate.orEmpty(),
    city = city.asExternalModel(),
    country = country.asExternalModel(),
    homeTown = homeTown.orEmpty(),
    photosInfo = getUserPhotosInfo(),
    mobilePhone = mobilePhone.orEmpty(),
    homePhone = homePhone.orEmpty(),
    relation = relation,
    lastSeen = null,
    counters = counters.asExternalModel(),
    usersPermissions = getUserPermissions(),
    searchId = null,
    foundUnixMillis = null
)

fun UserResponse.getUserPhotosInfo() = UserPhotosInfo(
    photoId = photoId.orEmpty(),
    photo50Url = photo50Url.orEmpty(),
    photoMaxUrl = photoMaxUrl.orEmpty(),
    photoMaxOrigUrl = photoMaxOrigUrl.orEmpty()
)

fun UserResponse.getUserPermissions() = UserPermissions(
    isClosed = isClosed,
    canAccessClosed = canAccessClosed,
    canWritePrivateMessage = canWritePrivateMessage?.let { it == 1 },
    canSendFriendRequest = canSendFriendRequest?.let { it == 1 }
)