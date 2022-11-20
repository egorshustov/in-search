package com.egorshustov.insearch.core.data.repository

import com.egorshustov.insearch.core.common.model.Result
import com.egorshustov.insearch.core.model.data.User
import com.egorshustov.insearch.core.model.data.requestsparams.GetUserRequestParams
import com.egorshustov.insearch.core.model.data.requestsparams.SearchUsersRequestParams
import com.egorshustov.insearch.core.model.data.requestsparams.VkCommonRequestParams
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