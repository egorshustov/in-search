package com.egorshustov.vpoiske.core.domain.token

import com.egorshustov.vpoiske.core.common.base.UseCase
import com.egorshustov.vpoiske.core.common.network.AppDispatchers.IO
import com.egorshustov.vpoiske.core.common.network.Dispatcher
import com.egorshustov.vpoiske.core.datastore.PreferenceStorage
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

data class SaveAccessTokenUseCaseParams(
    val accessToken: String
)

class SaveAccessTokenUseCase @Inject constructor(
    private val preferenceStorage: PreferenceStorage,
    @Dispatcher(IO) ioDispatcher: CoroutineDispatcher
) : UseCase<SaveAccessTokenUseCaseParams, Unit>(ioDispatcher) {

    override suspend fun execute(parameters: SaveAccessTokenUseCaseParams) {
        preferenceStorage.saveAccessToken(parameters.accessToken)
    }
}