package com.egorshustov.vpoiske.core.common.exceptions

sealed class DatabaseException(cause: Throwable? = null) : Exception(cause) {

    class EntriesNotAddedException : DatabaseException()

    class EntriesNotFoundException : DatabaseException()

    class RequestException(cause: Throwable) : DatabaseException(cause)
}
