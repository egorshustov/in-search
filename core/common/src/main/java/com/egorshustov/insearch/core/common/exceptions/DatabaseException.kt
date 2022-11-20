package com.egorshustov.insearch.core.common.exceptions

sealed class DatabaseException(override val cause: Throwable? = null) : Exception(cause) {

    class EntriesNotAddedException : DatabaseException()

    class EntriesNotFoundException : DatabaseException()

    class RequestException(cause: Throwable) : DatabaseException(cause)
}
