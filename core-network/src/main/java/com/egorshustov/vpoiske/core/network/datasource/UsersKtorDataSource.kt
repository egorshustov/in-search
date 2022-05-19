package com.egorshustov.vpoiske.core.network.datasource

import com.egorshustov.vpoiske.core.common.model.Result
import com.egorshustov.vpoiske.core.model.data.SearchUsersRequestParams
import com.egorshustov.vpoiske.core.model.data.VkCommonRequestParams
import com.egorshustov.vpoiske.core.network.model.SearchUserResponse
import com.egorshustov.vpoiske.core.network.model.SearchUsersResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UsersKtorDataSource(
    private val httpClient: HttpClient,
    private val baseUrl: String
) : UsersNetworkDataSource {

    override suspend fun searchUsers(
        searchUsersParams: SearchUsersRequestParams,
        commonParams: VkCommonRequestParams
    ): Flow<Result<List<SearchUserResponse>>> = flow {
        httpClient.get {
            // todo add params
        }.body<SearchUsersResponse>()
        // todo handle response
    }
}