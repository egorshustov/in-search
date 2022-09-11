package com.egorshustov.vpoiske.core.database.datasource

import com.egorshustov.vpoiske.core.common.exceptions.DatabaseException
import com.egorshustov.vpoiske.core.common.model.Result
import com.egorshustov.vpoiske.core.common.network.AppDispatchers.IO
import com.egorshustov.vpoiske.core.common.network.Dispatcher
import com.egorshustov.vpoiske.core.database.dao.UserDao
import com.egorshustov.vpoiske.core.database.model.UserEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class UsersRoomDataSource @Inject constructor(
    private val userDao: UserDao,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
) : UsersDatabaseDataSource {

    override fun getUsersStream(searchId: Long): Flow<List<UserEntity>> =
        userDao.getUsersStream(searchId).flowOn(ioDispatcher)

    override fun getUsersCountStream(searchId: Long): Flow<Int> =
        userDao.getUsersCountStream(searchId).flowOn(ioDispatcher)

    override suspend fun saveUser(entity: UserEntity): Result<Long> = withContext(ioDispatcher) {
        try {
            val userId = userDao.insertOrIgnoreUser(entity)
            if (userId != -1L) {
                return@withContext Result.Success(userId)
            } else {
                return@withContext Result.Error(DatabaseException.EntriesNotAddedException())
            }
        } catch (e: Exception) {
            return@withContext Result.Error(DatabaseException.RequestException(e))
        }
    }

    override suspend fun saveUsers(entities: List<UserEntity>): Result<List<Long>> =
        withContext(ioDispatcher) {
            try {
                val usersIds = userDao.insertOrIgnoreUsers(entities)
                if (usersIds.isNotEmpty()) {
                    return@withContext Result.Success(usersIds)
                } else {
                    return@withContext Result.Error(DatabaseException.EntriesNotAddedException())
                }
            } catch (e: Exception) {
                return@withContext Result.Error(DatabaseException.RequestException(e))
            }
        }
}