package com.egorshustov.vpoiske.core.common.exceptions

sealed class NetworkException(
    message: String? = null,
    cause: Throwable? = null
) : Exception(message, cause) {

    class VkException(
        override val cause: Throwable? = null,
        override val message: String? = null,
        val vkErrorCode: Int? = null
    ) : NetworkException(message, cause)
}

val NetworkException.VkException.needToWait: Boolean
    get() = vkErrorCode == VkApiError.TOO_MANY_REQUESTS_PER_SECOND.code
            || vkErrorCode == VkApiError.FLOOD_CONTROL.code
