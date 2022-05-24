package com.egorshustov.vpoiske.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CountersResponse(

    @SerialName("albums")
    val albums: Int? = null,

    @SerialName("videos")
    val videos: Int? = null,

    @SerialName("audios")
    val audios: Int? = null,

    @SerialName("photos")
    val photos: Int? = null,

    @SerialName("notes")
    val notes: Int? = null,

    @SerialName("gifts")
    val gifts: Int? = null,

    @SerialName("articles")
    val articles: Int? = null,

    @SerialName("friends")
    val friends: Int? = null,

    @SerialName("groups")
    val groups: Int? = null,

    @SerialName("mutual_friends")
    val mutualFriends: Int? = null,

    @SerialName("user_photos")
    val userPhotos: Int? = null,

    @SerialName("user_videos")
    val userVideos: Int? = null,

    @SerialName("followers")
    val followers: Int? = null,

    @SerialName("clips_followers")
    val clipsFollowers: Int? = null,

    @SerialName("subscriptions")
    val subscriptions: Int? = null,

    @SerialName("pages")
    val pages: Int? = null
)