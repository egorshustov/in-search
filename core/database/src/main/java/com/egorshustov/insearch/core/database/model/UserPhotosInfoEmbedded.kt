package com.egorshustov.insearch.core.database.model

import androidx.room.ColumnInfo
import com.egorshustov.insearch.core.common.utils.UrlString
import com.egorshustov.insearch.core.model.data.UserPhotosInfo

data class UserPhotosInfoEmbedded(

    @ColumnInfo(name = "photo_id")
    val photoId: String,

    @ColumnInfo(name = "photo_50_url")
    val photo50: UrlString,

    @ColumnInfo(name = "photo_max_url")
    val photoMax: UrlString,

    @ColumnInfo(name = "photo_max_orig_url")
    val photoMaxOrig: UrlString
)

internal fun UserPhotosInfoEmbedded.asExternalModel() = UserPhotosInfo(
    photoId = photoId,
    photo50 = photo50,
    photoMax = photoMax,
    photoMaxOrig = photoMaxOrig
)