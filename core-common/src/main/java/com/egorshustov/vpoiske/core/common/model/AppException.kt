package com.egorshustov.vpoiske.core.common.model

data class AppException(
    override val cause: Throwable? = null,
    override val message: String? = null,
    val vkErrorCode: Int? = null
) : Exception(message, cause)
