package com.egorshustov.vpoiske.core.database.model

import androidx.room.ColumnInfo
import com.egorshustov.vpoiske.core.model.data.UserPhotosInfo

data class UserPhotosInfoEmbedded(

    @ColumnInfo(name = "photo_id")
    val photoId: String,

    @ColumnInfo(name = "photo_50_url")
    val photo50Url: String,

    @ColumnInfo(name = "photo_max_url")
    val photoMaxUrl: String,

    @ColumnInfo(name = "photo_max_orig_url")
    val photoMaxOrigUrl: String
)

internal fun UserPhotosInfoEmbedded.asExternalModel() = UserPhotosInfo(
    photoId = photoId,
    photo50Url = photo50Url,
    photoMaxUrl = photoMaxUrl,
    photoMaxOrigUrl = photoMaxOrigUrl
)