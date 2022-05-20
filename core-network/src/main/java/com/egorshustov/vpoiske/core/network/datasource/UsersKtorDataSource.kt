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

    override fun searchUsers(
        searchUsersParams: SearchUsersRequestParams,
        commonParams: VkCommonRequestParams
    ): Flow<Result<List<SearchUserResponse>>> = flow {
        emit(Result.Loading)

        try {
            val searchUserResponseList = httpClient.get("$baseUrl/users.search") {
                parameter("country", searchUsersParams.countryId)
                parameter("city", searchUsersParams.cityId)
                parameter("age_from", searchUsersParams.ageFrom)
                parameter("age_to", searchUsersParams.ageTo)
                parameter("birth_day", searchUsersParams.birthDay)
                parameter("birth_month", searchUsersParams.birthMonth)
                parameter("fields", searchUsersParams.fields)
                parameter("hometown", searchUsersParams.homeTown)
                parameter("status", searchUsersParams.relation)
                parameter("sex", searchUsersParams.sex)
                parameter("has_photo", searchUsersParams.hasPhoto)
                parameter("count", searchUsersParams.count)
                parameter("sort", searchUsersParams.sortType)
                parameter("access_token", commonParams.accessToken)
                parameter("v", commonParams.apiVersion)
            }.body<SearchUsersResponse>().response?.searchUserResponseList.orEmpty()

            emit(Result.Success(searchUserResponseList))
        } catch (e: Throwable) {
            emit(Result.Error(e))
        }
    }
}