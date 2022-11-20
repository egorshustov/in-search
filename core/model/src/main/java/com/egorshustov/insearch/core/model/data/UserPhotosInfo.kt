package com.egorshustov.insearch.core.model.data

import com.egorshustov.insearch.core.common.utils.UrlString

data class UserPhotosInfo(
    val photoId: String,
    val photo50: UrlString,
    val photoMax: UrlString,
    val photoMaxOrig: UrlString
)