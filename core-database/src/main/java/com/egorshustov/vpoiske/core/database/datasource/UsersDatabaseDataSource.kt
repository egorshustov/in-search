package com.egorshustov.vpoiske.core.database.datasource

import com.egorshustov.vpoiske.core.common.model.Result
import com.egorshustov.vpoiske.core.database.model.UserEntity
import kotlinx.coroutines.flow.Flow

interface UsersDatabaseDataSource {

    fun getUsersStream(searchId: Long): Flow<List<UserEntity>>

    suspend fun saveUser(entity: UserEntity): Result<Long>

    suspend fun saveUsers(entities: List<UserEntity>): Result<List<Long>>
}