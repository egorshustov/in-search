package com.egorshustov.insearch.core.model.data.requestsparams

import com.egorshustov.insearch.core.common.network.DEFAULT_API_VERSION
import java.util.*

data class VkCommonRequestParams(
    val accessToken: String,
    val apiVersion: String = DEFAULT_API_VERSION,
    val responseLanguage: String? = if (Locale.getDefault().language == "ru") {
        "ru"
    } else {
        "en"
    }
)