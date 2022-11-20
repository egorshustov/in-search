package com.egorshustov.insearch.core.domain.token

import com.egorshustov.insearch.core.common.base.SafeUseCase
import com.egorshustov.insearch.core.common.network.AppDispatchers.IO
import com.egorshustov.insearch.core.common.network.Dispatcher
import com.egorshustov.insearch.core.datastore.PreferenceStorage
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

data class SaveAccessTokenUseCaseParams(
    val accessToken: String
)

class SaveAccessTokenUseCase @Inject constructor(
    private val preferenceStorage: PreferenceStorage,
    @Dispatcher(IO) ioDispatcher: CoroutineDispatcher
) : SafeUseCase<SaveAccessTokenUseCaseParams, Unit>(ioDispatcher) {

    override suspend fun execute(parameters: SaveAccessTokenUseCaseParams) {
        preferenceStorage.saveAccessToken(parameters.accessToken)
    }
}