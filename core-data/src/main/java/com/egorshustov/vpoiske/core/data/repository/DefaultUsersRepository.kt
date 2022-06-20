package com.egorshustov.vpoiske.core.data.repository

import com.egorshustov.vpoiske.core.common.model.Result
import com.egorshustov.vpoiske.core.common.model.mapResult
import com.egorshustov.vpoiske.core.common.network.AppDispatchers
import com.egorshustov.vpoiske.core.common.network.Dispatcher
import com.egorshustov.vpoiske.core.data.mappers.asEntity
import com.egorshustov.vpoiske.core.data.mappers.asEntityList
import com.egorshustov.vpoiske.core.database.datasource.UsersDatabaseDataSource
import com.egorshustov.vpoiske.core.database.model.asExternalModelList
import com.egorshustov.vpoiske.core.model.data.User
import com.egorshustov.vpoiske.core.model.data.requestsparams.GetUserRequestParams
import com.egorshustov.vpoiske.core.model.data.requestsparams.SearchUsersRequestParams
import com.egorshustov.vpoiske.core.model.data.requestsparams.VkCommonRequestParams
import com.egorshustov.vpoiske.core.network.datasource.UsersNetworkDataSource
import com.egorshustov.vpoiske.core.network.model.getuser.asExternalModel
import com.egorshustov.vpoiske.core.network.model.searchuser.asExternalModelList
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