package com.egorshustov.vpoiske.core.data.repository

import com.egorshustov.vpoiske.core.common.model.Result
import com.egorshustov.vpoiske.core.model.data.User
import com.egorshustov.vpoiske.core.model.data.requestsparams.GetUserRequestParams
import com.egorshustov.vpoiske.core.model.data.requestsparams.SearchUsersRequestParams
import com.egorshustov.vpoiske.core.model.data.requestsparams.VkCommonRequestParams
import kotlinx.coroutines.flow.Flow

interface UsersRepository {

    fun getUsersStream(searchId: Long): Flow<List<User>>

    fun getUsersCountStream(searchId: Long): Flow<Int>

    suspend fun saveUser(user: User): Result<Long>

    suspend fun saveUsers(users: List<User>): Result<List<Long>>

    fun searchUsers(
        searchUsersParams: SearchUsersRequestParams,
        commonParams: VkCommonRequestParams
    ): Flow<Result<List<User>>>

    fun getUser(
        getUserParams: GetUserRequestParams,
        commonParams: VkCommonRequestParams
    ): Flow<Result<User>>
}