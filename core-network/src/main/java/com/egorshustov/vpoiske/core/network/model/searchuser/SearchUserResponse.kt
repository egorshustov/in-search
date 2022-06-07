package com.egorshustov.vpoiske.core.network.model.searchuser

import com.egorshustov.vpoiske.core.common.utils.NO_VALUE
import com.egorshustov.vpoiske.core.common.utils.UrlString
import com.egorshustov.vpoiske.core.model.data.User
import com.egorshustov.vpoiske.core.model.data.UserCounters
import com.egorshustov.vpoiske.core.model.data.UserPermissions
import com.egorshustov.vpoiske.core.model.data.UserPhotosInfo
import com.egorshustov.vpoiske.core.network.model.getcities.CityResponse
import com.egorshustov.vpoiske.core.network.model.getcities.asExternalModel
import com.egorshustov.vpoiske.core.network.model.getcountries.CountryResponse
import com.egorshustov.vpoiske.core.network.model.getcountries.asExternalModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchUserResponse(

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
    val photoId: String? = null,

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
    val relation: Int? = null,

    @SerialName("last_seen")
    val lastSeen: UserLastSeenResponse? = null,

    @SerialName("followers_count")
    val followersCount: Int? = null
)

fun SearchUserResponse.asExternalModel() = User(
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
    lastSeen = lastSeen.asExternalModel(),
    counters = getUserCounters(),
    permissions = getUserPermissions(),
    searchId = null,
    foundTime = null
)

fun List<SearchUserResponse>.asExternalModelList(): List<User> = map { it.asExternalModel() }

fun SearchUserResponse.getUserPhotosInfo() = UserPhotosInfo(
    photoId = photoId.orEmpty(),
    photo50 = photo50.orEmpty(),
    photoMax = photoMax.orEmpty(),
    photoMaxOrig = photoMaxOrig.orEmpty()
)

fun SearchUserResponse.getUserCounters() = UserCounters(
    albums = null,
    videos = null,
    audios = null,
    photos = null,
    notes = null,
    gifts = null,
    articles = null,
    friends = null,
    groups = null,
    mutualFriends = null,
    userPhotos = null,
    userVideos = null,
    followers = followersCount,
    clipsFollowers = null,
    subscriptions = null,
    pages = null
)

fun SearchUserResponse.getUserPermissions() = UserPermissions(
    isClosed = isClosed,
    canAccessClosed = canAccessClosed,
    canWritePrivateMessage = canWritePrivateMessage?.let { it == 1 },
    canSendFriendRequest = canSendFriendRequest?.let { it == 1 }
)