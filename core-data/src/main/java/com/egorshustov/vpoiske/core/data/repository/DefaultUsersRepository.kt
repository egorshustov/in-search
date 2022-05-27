package com.egorshustov.vpoiske.core.data.repository

import com.egorshustov.vpoiske.core.common.model.Result
import com.egorshustov.vpoiske.core.common.network.AppDispatchers
import com.egorshustov.vpoiske.core.common.network.Dispatcher
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
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class DefaultUsersRepository @Inject constructor(
    private val usersNetworkDataSource: UsersNetworkDataSource,
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : UsersRepository {

    override fun searchUsers(
        searchUsersParams: SearchUsersRequestParams,
        commonParams: VkCommonRequestParams
    ): Flow<Result<List<User>>> = usersNetworkDataSource
        .searchUsers(searchUsersParams, commonParams)
        .map {
            when (it) {
                is Result.Success -> Result.Success(it.data.asExternalModelList())
                is Result.Error -> Result.Error(it.exception)
                Result.Loading -> Result.Loading
            }
        }.flowOn(ioDispatcher)

    override fun getUser(
        getUserParams: GetUserRequestParams,
        commonParams: VkCommonRequestParams
    ): Flow<Result<User>> = usersNetworkDataSource
        .getUser(getUserParams, commonParams)
        .map {
            when (it) {
                is Result.Success -> Result.Success(it.data.asExternalModel())
                is Result.Error -> Result.Error(it.exception)
                Result.Loading -> Result.Loading
            }
        }.flowOn(ioDispatcher)
}