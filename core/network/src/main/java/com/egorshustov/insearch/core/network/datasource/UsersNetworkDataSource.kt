package com.egorshustov.insearch.core.network.datasource

import com.egorshustov.insearch.core.common.model.Result
import com.egorshustov.insearch.core.model.data.requestsparams.GetUserRequestParams
import com.egorshustov.insearch.core.model.data.requestsparams.SearchUsersRequestParams
import com.egorshustov.insearch.core.model.data.requestsparams.VkCommonRequestParams
import com.egorshustov.insearch.core.network.model.getuser.UserResponse
import com.egorshustov.insearch.core.network.model.searchuser.SearchUserResponse
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
        getUserParams: GetUserRequestParams,
        commonParams: VkCommonRequestParams
    ): Flow<Result<UserResponse>>
}