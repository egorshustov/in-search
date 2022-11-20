package com.egorshustov.insearch.core.domain.search

import androidx.paging.PagingData
import androidx.paging.map
import com.egorshustov.insearch.core.common.base.FlowUseCase
import com.egorshustov.insearch.core.common.model.Result
import com.egorshustov.insearch.core.common.network.AppDispatchers.IO
import com.egorshustov.insearch.core.common.network.Dispatcher
import com.egorshustov.insearch.core.common.utils.asResult
import com.egorshustov.insearch.core.data.repository.SearchesRepository
import com.egorshustov.insearch.core.model.data.SearchWithUsers
import com.egorshustov.insearch.core.model.data.SearchWithUsersPhotos
import com.egorshustov.insearch.core.model.data.requestsparams.PagingConfigParams
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

data class GetSearchesWithUsersPhotosParams(
    val params: PagingConfigParams,
    val photosMaxCount: Int = 9
)

class GetSearchesWithUsersPhotosUseCase @Inject constructor(
    private val searchesRepository: SearchesRepository,
    @Dispatcher(IO) ioDispatcher: CoroutineDispatcher
) : FlowUseCase<GetSearchesWithUsersPhotosParams, PagingData<SearchWithUsersPhotos>>(ioDispatcher) {

    override fun execute(parameters: GetSearchesWithUsersPhotosParams): Flow<Result<PagingData<SearchWithUsersPhotos>>> =
        searchesRepository
            .getSearchesWithUsersStream(parameters.params)
            .map { pagingSearchWithUsers ->
                pagingSearchWithUsers.map {
                    it.toSearchesWithSomeUsersPhotos(photosMaxCount = parameters.photosMaxCount)
                }
            }
            .asResult()

    private fun SearchWithUsers.toSearchesWithSomeUsersPhotos(photosMaxCount: Int) =
        SearchWithUsersPhotos(
            search = search,
            photos = users.sortedBy { it.foundTime }
                .take(photosMaxCount)
                .map { it.photosInfo.photo50 }
        )
}