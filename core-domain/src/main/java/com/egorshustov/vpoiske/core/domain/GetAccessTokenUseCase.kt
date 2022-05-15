package com.egorshustov.vpoiske.core.domain

import com.egorshustov.vpoiske.core.common.base.FlowUseCase
import com.egorshustov.vpoiske.core.common.model.Result
import com.egorshustov.vpoiske.core.common.network.IoDispatcher
import com.egorshustov.vpoiske.core.datastore.PreferenceStorage
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAccessTokenUseCase @Inject constructor(
    private val preferenceStorage: PreferenceStorage,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : FlowUseCase<Unit, String>(dispatcher) {

    override fun execute(parameters: Unit): Flow<Result<String>> =
        preferenceStorage.accessToken.map { Result.Success(it) }
}