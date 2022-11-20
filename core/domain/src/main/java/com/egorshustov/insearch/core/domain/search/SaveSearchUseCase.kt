package com.egorshustov.insearch.core.domain.search

import com.egorshustov.insearch.core.common.base.UseCase
import com.egorshustov.insearch.core.common.model.Result
import com.egorshustov.insearch.core.common.network.AppDispatchers.IO
import com.egorshustov.insearch.core.common.network.Dispatcher
import com.egorshustov.insearch.core.data.repository.SearchesRepository
import com.egorshustov.insearch.core.model.data.Search
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

data class SaveSearchUseCaseParams(val search: Search)

class SaveSearchUseCase @Inject constructor(
    private val searchesRepository: SearchesRepository,
    @Dispatcher(IO) ioDispatcher: CoroutineDispatcher
) : UseCase<SaveSearchUseCaseParams, Result<Long>>(ioDispatcher) {

    override suspend fun execute(parameters: SaveSearchUseCaseParams): Result<Long> =
        searchesRepository.saveSearch(parameters.search)
}