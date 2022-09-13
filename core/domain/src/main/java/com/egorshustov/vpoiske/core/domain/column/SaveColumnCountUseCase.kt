package com.egorshustov.vpoiske.core.domain.column

import com.egorshustov.vpoiske.core.common.base.SafeUseCase
import com.egorshustov.vpoiske.core.common.network.AppDispatchers.IO
import com.egorshustov.vpoiske.core.common.network.Dispatcher
import com.egorshustov.vpoiske.core.datastore.PreferenceStorage
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

data class SaveColumnCountUseCaseParams(
    val columnCount: Int
)

class SaveColumnCountUseCase @Inject constructor(
    private val preferenceStorage: PreferenceStorage,
    @Dispatcher(IO) ioDispatcher: CoroutineDispatcher
) : SafeUseCase<SaveColumnCountUseCaseParams, Unit>(ioDispatcher) {

    override suspend fun execute(parameters: SaveColumnCountUseCaseParams) {
        preferenceStorage.saveSelectedColumnCount(parameters.columnCount)
    }
}