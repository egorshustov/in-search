package com.egorshustov.auth.impl.domain

import com.egorshustov.core.common.base.FlowUseCase
import com.egorshustov.core.common.model.Result
import com.egorshustov.core.common.prefs.PreferenceStorage
import com.egorshustov.core.common.utils.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class GetAccessTokenUseCase @Inject constructor(
    private val preferenceStorage: PreferenceStorage,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : FlowUseCase<Unit, String>(dispatcher) {

    override fun execute(parameters: Unit): Flow<Result<String>> =
        preferenceStorage.accessToken.map { Result.Success(it) }
}