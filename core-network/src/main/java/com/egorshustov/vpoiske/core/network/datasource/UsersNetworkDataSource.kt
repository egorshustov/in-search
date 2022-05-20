package com.egorshustov.vpoiske.core.network.datasource

import com.egorshustov.vpoiske.core.common.model.Result
import com.egorshustov.vpoiske.core.model.data.SearchUsersRequestParams
import com.egorshustov.vpoiske.core.model.data.VkCommonRequestParams
import com.egorshustov.vpoiske.core.network.model.SearchUserResponse
import kotlinx.coroutines.flow.Flow

interface UsersNetworkDataSource {

    fun searchUsers(
        searchUsersParams: SearchUsersRequestParams,
        commonParams: VkCommonRequestParams
    ): Flow<Result<List<SearchUserResponse>>>
}