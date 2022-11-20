package com.egorshustov.insearch.core.database.model

import androidx.room.ColumnInfo
import com.egorshustov.insearch.core.model.data.UserCounters

data class UserCountersEmbedded(

    val albums: Int?,

    val videos: Int?,

    val audios: Int?,

    val photos: Int?,

    val notes: Int?,

    val gifts: Int?,

    val articles: Int?,

    val friends: Int?,

    val groups: Int?,

    @ColumnInfo(name = "mutual_friends")
    val mutualFriends: Int?,

    @ColumnInfo(name = "user_photos")
    val userPhotos: Int?,

    @ColumnInfo(name = "user_videos")
    val userVideos: Int?,

    val followers: Int?,

    @ColumnInfo(name = "clips_followers")
    val clipsFollowers: Int?,

    val subscriptions: Int?,

    val pages: Int?
)

internal fun UserCountersEmbedded.asExternalModel() = UserCounters(
    albums = albums,
    videos = videos,
    audios = audios,
    photos = photos,
    notes = notes,
    gifts = gifts,
    articles = articles,
    friends = friends,
    groups = groups,
    mutualFriends = mutualFriends,
    userPhotos = userPhotos,
    userVideos = userVideos,
    followers = followers,
    clipsFollowers = clipsFollowers,
    subscriptions = subscriptions,
    pages = pages
)