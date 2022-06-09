package com.egorshustov.vpoiske.core.common.model

// TODO: refactor exceptions

open class AppException( // TODO:  This one probably should be VkNetworkException
    override val cause: Throwable? = null,
    override val message: String? = null,
    val vkErrorCode: Int? = null
) : Exception(message, cause)

class DbEntriesNotAddedException : AppException()

class DbRequestException(cause: Throwable) : AppException(cause = cause)
