package com.egorshustov.insearch.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
abstract class BaseVkResponse {

    @SerialName("error")
    abstract val error: VkErrorResponse?
}