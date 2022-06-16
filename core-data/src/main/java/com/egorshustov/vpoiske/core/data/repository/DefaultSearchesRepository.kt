package com.egorshustov.vpoiske.core.data.repository

import androidx.paging.PagingData
import com.egorshustov.vpoiske.core.common.model.Result
import com.egorshustov.vpoiske.core.common.network.AppDispatchers
import com.egorshustov.vpoiske.core.common.network.Dispatcher
import com.egorshustov.vpoiske.core.data.mappers.asEntity
import com.egorshustov.vpoiske.core.data.mappers.asPagingDataStream
import com.egorshustov.vpoiske.core.database.datasource.SearchesDatabaseDataSource
import com.egorshustov.vpoiske.core.database.model.asExternalModel
import com.egorshustov.vpoiske.core.model.data.Search
import com.egorshustov.vpoiske.core.model.data.SearchWithUsers
import com.egorshustov.vpoiske.core.model.data.requestsparams.PagingConfigParams
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class DefaultSearchesRepository @Inject constructor(
    private val searchesDatabaseDataSource: SearchesDatabaseDataSource,
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : SearchesRepository {

    override fun getSearchesWithUsersStream(params: PagingConfigParams): Flow<PagingData<SearchWithUsers>> =
        searchesDatabaseDataSource.getSearchesWithUsers().asPagingDataStream(params) {
            it.asExternalModel()
        }.flowOn(ioDispatcher)

    override fun getLastSearchIdStream(): Flow<Long?> = searchesDatabaseDataSource
        .getLastSearchIdStream()
        .flowOn(ioDispatcher)

    override suspend fun getSearch(id: Long): Result<Search> = withContext(ioDispatcher) {
        when (val result = searchesDatabaseDataSource.getSearch(id)) {
            is Result.Success -> Result.Success(result.data.asExternalModel())
            is Result.Error -> Result.Error(result.exception)
            Result.Loading -> Result.Loading
        }
    }

    override suspend fun saveSearch(search: Search): Result<Long> = withContext(ioDispatcher) {
        searchesDatabaseDataSource.saveSearch(search.asEntity())
    }

    override suspend fun deleteSearch(id: Long) = withContext(ioDispatcher) {
        searchesDatabaseDataSource.deleteSearch(id)
    }
}