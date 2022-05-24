package com.egorshustov.vpoiske.core.network.datasource

import com.egorshustov.vpoiske.core.common.model.Result
import com.egorshustov.vpoiske.core.model.data.requestsparams.SearchUsersRequestParams
import com.egorshustov.vpoiske.core.model.data.requestsparams.UserGetRequestParams
import com.egorshustov.vpoiske.core.model.data.requestsparams.VkCommonRequestParams
import com.egorshustov.vpoiske.core.network.model.SearchUserResponse
import com.egorshustov.vpoiske.core.network.model.UserResponse
import kotlinx.coroutines.flow.Flow

interface UsersNetworkDataSource {

    /**
     * [users.search VK API](https://dev.vk.com/method/users.search)
     */
    fun searchUsers(
        searchUsersParams: SearchUsersRequestParams,
        commonParams: VkCommonRequestParams
    ): Flow<Result<List<SearchUserResponse>>>

    /**
     * [users.get VK API](https://dev.vk.com/method/users.get)
     */
    fun getUser(
        userGetRequestParams: UserGetRequestParams,
        commonParams: VkCommonRequestParams
    ): Flow<Result<List<UserResponse>>>
}