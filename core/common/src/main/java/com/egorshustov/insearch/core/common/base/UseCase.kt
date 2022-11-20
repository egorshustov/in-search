package com.egorshustov.insearch.core.common.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

/**
 * Executes business logic synchronously or asynchronously using Coroutines.
 */
abstract class UseCase<in P, R>(private val coroutineDispatcher: CoroutineDispatcher) {

    /** Executes the use case asynchronously and returns a result.
     *
     * @param parameters the input parameters to run the use case with
     */
    suspend operator fun invoke(parameters: P): R = withContext(coroutineDispatcher) {
        execute(parameters)
    }

    /**
     * Override this to set the code to be executed.
     */
    @Throws(RuntimeException::class)
    protected abstract suspend fun execute(parameters: P): R
}
