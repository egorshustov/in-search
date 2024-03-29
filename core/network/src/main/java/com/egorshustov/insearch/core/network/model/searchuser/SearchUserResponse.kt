package com.egorshustov.insearch.core.network.model.searchuser

import com.egorshustov.insearch.core.common.utils.NO_VALUE_L
import com.egorshustov.insearch.core.common.utils.UrlString
import com.egorshustov.insearch.core.model.data.*
import com.egorshustov.insearch.core.network.model.getcities.CityResponse
import com.egorshustov.insearch.core.network.model.getcities.asExternalModel
import com.egorshustov.insearch.core.network.model.getcountries.CountryResponse
import com.egorshustov.insearch.core.network.model.getcountries.asExternalModel
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
    val relationId: Int? = null,

    @SerialName("last_seen")
    val lastSeen: UserLastSeenResponse? = null,

    @SerialName("followers_count")
    val followersCount: Int? = null
)

fun SearchUserResponse.asExternalModel() = User(
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