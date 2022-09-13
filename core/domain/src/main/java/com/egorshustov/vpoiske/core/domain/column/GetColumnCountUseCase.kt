package com.egorshustov.vpoiske.core.domain.column

import com.egorshustov.vpoiske.core.common.base.FlowUseCase
import com.egorshustov.vpoiske.core.common.model.Result
import com.egorshustov.vpoiske.core.common.network.AppDispatchers.IO
import com.egorshustov.vpoiske.core.common.network.Dispatcher
import com.egorshustov.vpoiske.core.common.utils.asResult
import com.egorshustov.vpoiske.core.datastore.PreferenceStorage
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetColumnCountUseCase @Inject constructor(
    private val preferenceStorage: PreferenceStorage,
    @Dispatcher(IO) ioDispatcher: CoroutineDispatcher
) : FlowUseCase<Unit, Int>(ioDispatcher) {

    override fun execute(parameters: Unit): Flow<Result<Int>> =
        preferenceStorage.selectedColumnCount.asResult()
}