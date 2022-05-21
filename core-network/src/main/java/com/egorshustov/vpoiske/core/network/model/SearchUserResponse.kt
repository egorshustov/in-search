package com.egorshustov.vpoiske.core.network.model

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
    val bDate: String? = null,

    @SerialName("city")
    val city: CityResponse? = null,

    @SerialName("country")
    val country: CountryResponse? = null,

    @SerialName("home_town")
    val homeTown: String? = null,

    @SerialName("photo_50")
    val photo50: String? = null,

    @SerialName("photo_max")
    val photoMax: String? = null,

    @SerialName("photo_max_orig")
    val photoMaxOrig: String? = null,

    @SerialName("photo_id")
    val photoId: String? = null,

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
    val lastSeen: LastSeenResponse? = null,

    @SerialName("followers_count")
    val followersCount: Int? = null
)