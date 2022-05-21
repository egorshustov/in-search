package com.egorshustov.vpoiske.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VkErrorResponse(

    @SerialName("error_code")
    val errorCode: Int? = null,

    @SerialName("error_msg")
    val errorMessage: String? = null
)