package com.egorshustov.insearch.core.database.datasource

import com.egorshustov.insearch.core.common.model.Result
import com.egorshustov.insearch.core.database.model.UserEntity
import kotlinx.coroutines.flow.Flow

interface UsersDatabaseDataSource {

    fun getUsersStream(searchId: Long): Flow<List<UserEntity>>

    fun getUsersCountStream(searchId: Long): Flow<Int>

    suspend fun saveUser(entity: UserEntity): Result<Long>

    suspend fun saveUsers(entities: List<UserEntity>): Result<List<Long>>
}