package com.egorshustov.insearch.core.data.repository

import com.egorshustov.insearch.core.common.model.Result
import com.egorshustov.insearch.core.common.network.AppDispatchers
import com.egorshustov.insearch.core.common.network.Dispatcher
import com.egorshustov.insearch.core.common.utils.mapResult
import com.egorshustov.insearch.core.data.mappers.asEntity
import com.egorshustov.insearch.core.data.mappers.asEntityList
import com.egorshustov.insearch.core.database.datasource.UsersDatabaseDataSource
import com.egorshustov.insearch.core.database.model.asExternalModelList
import com.egorshustov.insearch.core.model.data.User
import com.egorshustov.insearch.core.model.data.requestsparams.GetUserRequestParams
import com.egorshustov.insearch.core.model.data.requestsparams.SearchUsersRequestParams
import com.egorshustov.insearch.core.model.data.requestsparams.VkCommonRequestParams
import com.egorshustov.insearch.core.network.datasource.UsersNetworkDataSource
import com.egorshustov.insearch.core.network.model.getuser.asExternalModel
import com.egorshustov.insearch.core.network.model.searchuser.asExternalModelList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class DefaultUsersRepository @Inject constructor(
    private val usersNetworkDataSource: UsersNetworkDataSource,
    private val usersDatabaseDataSource: UsersDatabaseDataSource,
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : UsersRepository {

    override fun getUsersStream(searchId: Long): Flow<List<User>> = usersDatabaseDataSource
        .getUsersStream(searchId)
        .map { it.asExternalModelList() }
        .flowOn(ioDispatcher)

    override fun getUsersCountStream(searchId: Long): Flow<Int> = usersDatabaseDataSource
        .getUsersCountStream(searchId)
        .flowOn(ioDispatcher)

    override suspend fun saveUser(user: User): Result<Long> = withContext(ioDispatcher) {
        usersDatabaseDataSource.saveUser(user.asEntity())
    }

    override suspend fun saveUsers(users: List<User>): Result<List<Long>> =
        withContext(ioDispatcher) { usersDatabaseDataSource.saveUsers(users.asEntityList()) }

    override fun searchUsers(
        searchUsersParams: SearchUsersRequestParams,
        commonParams: VkCommonRequestParams
    ): Flow<Result<List<User>>> = usersNetworkDataSource
        .searchUsers(searchUsersParams, commonParams)
        .mapResult { it.asExternalModelList() }
        .flowOn(ioDispatcher)

    override fun getUser(
        getUserParams: GetUserRequestParams,
        commonParams: VkCommonRequestParams
    ): Flow<Result<User>> = usersNetworkDataSource
        .getUser(getUserParams, commonParams)
        .mapResult { it.asExternalModel() }
        .flowOn(ioDispatcher)
}