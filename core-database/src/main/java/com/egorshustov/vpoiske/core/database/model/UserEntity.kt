package com.egorshustov.vpoiske.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.egorshustov.vpoiske.core.common.utils.NO_VALUE

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    var id: Long,

    @ColumnInfo(name = "first_name")
    val firstName: String,

    @ColumnInfo(name = "last_name")
    val lastName: String,

    val sex: Int,

    @ColumnInfo(name = "b_date")
    val birthDate: String,

    @ColumnInfo(name = "city_id")
    val cityId: Int,

    @ColumnInfo(name = "city_title")
    val cityTitle: String,

    @ColumnInfo(name = "country_id")
    val countryId: Int,

    @ColumnInfo(name = "country_title")
    val countryTitle: String,

    @ColumnInfo(name = "home_town")
    val homeTown: String?,

    @ColumnInfo(name = "photo_id")
    val photoId: String,

    @ColumnInfo(name = "photo_50")
    val photo50: String,

    @ColumnInfo(name = "photo_max")
    val photoMax: String,

    @ColumnInfo(name = "photo_max_orig")
    val photoMaxOrig: String,

    @ColumnInfo(name = "mobile_phone")
    val mobilePhone: String,

    @ColumnInfo(name = "home_phone")
    val homePhone: String,

    val relation: Int,

    val albums: Int,

    val videos: Int,

    val audios: Int,

    val photos: Int,

    val notes: Int,

    val gifts: Int,

    val friends: Int,

    val groups: Int,

    val followers: Int,

    val subscriptions: Int,

    val pages: Int,

    @ColumnInfo(name = "is_closed")
    val isClosed: Boolean,

    @ColumnInfo(name = "can_access_closed")
    val canAccessClosed: Boolean,

    @ColumnInfo(name = "can_write_private_message")
    val canWritePrivateMessage: Int,

    @ColumnInfo(name = "can_send_friend_request")
    val canSendFriendRequest: Int,

    @ColumnInfo(name = "mutual_friends")
    val mutualFriends: Int,

    @ColumnInfo(name = "user_photos")
    val userPhotos: Int,

    @ColumnInfo(name = "user_videos")
    val userVideos: Int
) {

    @ColumnInfo(name = "search_id")
    var searchId: Long = NO_VALUE.toLong()

    @ColumnInfo(name = "found_unix_millis")
    var foundUnixMillis: Long = NO_VALUE.toLong()
}