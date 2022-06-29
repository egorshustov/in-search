package com.egorshustov.vpoiske.core.model.data.requestsparams

import com.egorshustov.vpoiske.core.common.network.DEFAULT_API_VERSION

data class VkCommonRequestParams(
    val accessToken: String,
    val apiVersion: String = DEFAULT_API_VERSION,
    val responseLanguage: String? = null
)