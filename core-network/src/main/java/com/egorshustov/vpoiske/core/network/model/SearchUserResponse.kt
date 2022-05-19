package com.egorshustov.vpoiske.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchUserResponse(

    @SerialName("id")
    val id: Long?,

    @SerialName("first_name")
    val firstName: String?,

    @SerialName("last_name")
    val lastName: String?,

    @SerialName("is_closed")
    val isClosed: Boolean?,

    @SerialName("can_access_closed")
    val canAccessClosed: Boolean?,

    @SerialName("sex")
    val sex: Int?,

    @SerialName("bdate")
    val bDate: String?,

    @SerialName("city")
    val city: CityResponse?,

    @SerialName("country")
    val country: CountryResponse?,

    @SerialName("home_town")
    val homeTown: String?,

    @SerialName("photo_50")
    val photo50: String?,

    @SerialName("photo_max")
    val photoMax: String?,

    @SerialName("photo_max_orig")
    val photoMaxOrig: String?,

    @SerialName("photo_id")
    val photoId: String?,

    @SerialName("can_write_private_message")
    val canWritePrivateMessage: Int?,

    @SerialName("can_send_friend_request")
    val canSendFriendRequest: Int?,

    @SerialName("mobile_phone")
    val mobilePhone: String?,

    @SerialName("home_phone")
    val homePhone: String?,

    @SerialName("relation")
    val relation: Int?,

    @SerialName("last_seen")
    val lastSeen: LastSeenResponse?,

    @SerialName("followers_count")
    val followersCount: Int?
)