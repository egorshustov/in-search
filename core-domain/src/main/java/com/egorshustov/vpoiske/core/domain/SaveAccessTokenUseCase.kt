package com.egorshustov.vpoiske.core.domain

import com.egorshustov.vpoiske.core.common.base.UseCase
import com.egorshustov.vpoiske.core.common.network.IoDispatcher
import com.egorshustov.vpoiske.core.datastore.PreferenceStorage
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

data class SaveAccessTokenUseCaseParams(
    val accessToken: String
)

class SaveAccessTokenUseCase @Inject constructor(
    private val preferenceStorage: PreferenceStorage,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<SaveAccessTokenUseCaseParams, Unit>(dispatcher) {

    override suspend fun execute(parameters: SaveAccessTokenUseCaseParams) {
        preferenceStorage.saveAccessToken(parameters.accessToken)
    }
}