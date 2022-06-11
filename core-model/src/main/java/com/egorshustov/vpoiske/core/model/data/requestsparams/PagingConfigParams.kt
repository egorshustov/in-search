package com.egorshustov.vpoiske.core.model.data.requestsparams

data class PagingConfigParams(
    val pageSize: Int,
    val maxSize: Int,
    val enablePlaceholders: Boolean
)
