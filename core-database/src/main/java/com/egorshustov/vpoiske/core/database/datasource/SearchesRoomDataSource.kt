package com.egorshustov.vpoiske.core.database.datasource

import androidx.paging.PagingSource
import com.egorshustov.vpoiske.core.common.exceptions.DatabaseException
import com.egorshustov.vpoiske.core.common.model.Result
import com.egorshustov.vpoiske.core.common.network.AppDispatchers.IO
import com.egorshustov.vpoiske.core.common.network.Dispatcher
import com.egorshustov.vpoiske.core.database.dao.SearchDao
import com.egorshustov.vpoiske.core.database.model.SearchEntity
import com.egorshustov.vpoiske.core.database.model.SearchWithUsersPopulated
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class SearchesRoomDataSource @Inject constructor(
    private val searchDao: SearchDao,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
) : SearchesDatabaseDataSource {

    override fun getSearchesWithUsers(): PagingSource<Int, SearchWithUsersPopulated> =
        searchDao.getSearchesWithUsers()

    override fun getLastSearchIdStream(): Flow<Long?> =
        searchDao.getLastSearchIdStream().flowOn(ioDispatcher)

    override suspend fun getSearch(id: Long): Result<SearchEntity> =
        withContext(ioDispatcher) {
            try {
                val searchEntity = searchDao.getSearch(id)
                if (searchEntity != null) {
                    return@withContext Result.Success(searchEntity)
                } else {
                    return@withContext Result.Error(DatabaseException.EntriesNotFoundException())
                }
            } catch (e: Exception) {
                return@withContext Result.Error(DatabaseException.RequestException(e))
            }
        }

    override suspend fun saveSearch(entity: SearchEntity): Result<Long> =
        withContext(ioDispatcher) {
            try {
                val searchId = searchDao.insertSearch(entity)
                if (searchId != -1L) {
                    return@withContext Result.Success(searchId)
                } else {
                    return@withContext Result.Error(DatabaseException.EntriesNotAddedException())
                }
            } catch (e: Exception) {
                return@withContext Result.Error(DatabaseException.RequestException(e))
            }
        }

    override suspend fun deleteSearch(id: Long) = withContext(ioDispatcher) {
        searchDao.deleteSearch(id)
    }
}