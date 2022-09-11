package com.egorshustov.vpoiske.core.common.base

import com.egorshustov.vpoiske.core.common.model.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import timber.log.Timber

/**
 * Executes business logic synchronously or asynchronously using Coroutines.
 */
abstract class SafeUseCase<in P, R>(private val coroutineDispatcher: CoroutineDispatcher) {

    /** Executes the use case asynchronously and wrap it with [Result].
     *
     * @return a [Result].
     *
     * @param parameters the input parameters to run the use case with
     */
    suspend operator fun invoke(parameters: P): Result<R> = try {
        // Moving all use case's executions to the injected dispatcher
        // In production code, this is usually the Default dispatcher (background thread)
        // In tests, this becomes a TestCoroutineDispatcher
        withContext(coroutineDispatcher) {
            execute(parameters).let { Result.Success(it) }
        }
    } catch (e: Exception) {
        Timber.w(e)
        Result.Error(e)
    }

    /**
     * Override this to set the code to be executed.
     */
    @Throws(RuntimeException::class)
    protected abstract suspend fun execute(parameters: P): R
}
