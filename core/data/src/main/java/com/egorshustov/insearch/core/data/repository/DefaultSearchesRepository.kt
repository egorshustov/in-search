package com.egorshustov.insearch.core.data.repository

import androidx.paging.PagingData
import com.egorshustov.insearch.core.common.model.Result
import com.egorshustov.insearch.core.common.network.AppDispatchers
import com.egorshustov.insearch.core.common.network.Dispatcher
import com.egorshustov.insearch.core.common.utils.map
import com.egorshustov.insearch.core.data.mappers.asEntity
import com.egorshustov.insearch.core.data.mappers.asPagingDataStream
import com.egorshustov.insearch.core.database.datasource.SearchesDatabaseDataSource
import com.egorshustov.insearch.core.database.model.asExternalModel
import com.egorshustov.insearch.core.model.data.Search
import com.egorshustov.insearch.core.model.data.SearchWithUsers
import com.egorshustov.insearch.core.model.data.requestsparams.PagingConfigParams
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
        searchesDatabaseDataSource.getSearch(id).map { it.asExternalModel() }
    }

    override suspend fun getLastSearch(): Result<Search> = withContext(ioDispatcher) {
        searchesDatabaseDataSource.getLastSearch().map { it.asExternalModel() }
    }

    override suspend fun saveSearch(search: Search): Result<Long> = withContext(ioDispatcher) {
        searchesDatabaseDataSource.saveSearch(search.asEntity())
    }

    override suspend fun deleteSearch(id: Long) = withContext(ioDispatcher) {
        searchesDatabaseDataSource.deleteSearch(id)
    }
}