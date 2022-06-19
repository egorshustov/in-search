package com.egorshustov.vpoiske.core.domain.search

import com.egorshustov.vpoiske.core.common.base.UseCase
import com.egorshustov.vpoiske.core.common.network.AppDispatchers.IO
import com.egorshustov.vpoiske.core.common.network.Dispatcher
import com.egorshustov.vpoiske.core.data.repository.SearchesRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

data class DeleteSearchUseCaseParams(val searchId: Long)

class DeleteSearchUseCase @Inject constructor(
    private val searchesRepository: SearchesRepository,
    @Dispatcher(IO) ioDispatcher: CoroutineDispatcher
) : UseCase<DeleteSearchUseCaseParams, Unit>(ioDispatcher) {

    override suspend fun execute(parameters: DeleteSearchUseCaseParams) =
        searchesRepository.deleteSearch(parameters.searchId)
}