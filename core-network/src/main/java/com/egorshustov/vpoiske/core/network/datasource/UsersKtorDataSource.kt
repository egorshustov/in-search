package com.egorshustov.vpoiske.core.network.datasource

import com.egorshustov.vpoiske.core.common.exceptions.NetworkException
import com.egorshustov.vpoiske.core.common.model.Result
import com.egorshustov.vpoiske.core.common.network.AppDispatchers.IO
import com.egorshustov.vpoiske.core.common.network.Dispatcher
import com.egorshustov.vpoiske.core.common.utils.Seconds
import com.egorshustov.vpoiske.core.common.utils.toDuration
import com.egorshustov.vpoiske.core.model.data.requestsparams.GetUserRequestParams
import com.egorshustov.vpoiske.core.model.data.requestsparams.SearchUsersRequestParams
import com.egorshustov.vpoiske.core.model.data.requestsparams.VkCommonRequestParams
import com.egorshustov.vpoiske.core.network.AppBaseUrl
import com.egorshustov.vpoiske.core.network.isSuccessful
import com.egorshustov.vpoiske.core.network.model.getuser.GetUserResponse
import com.egorshustov.vpoiske.core.network.model.getuser.UserResponse
import com.egorshustov.vpoiske.core.network.model.searchuser.SearchUserResponse
import com.egorshustov.vpoiske.core.network.model.searchuser.SearchUsersResponse
import com.egorshustov.vpoiske.core.network.retryWhenFloodError
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class UsersKtorDataSource @Inject constructor(
    private val httpClient: HttpClient,
    @AppBaseUrl private val baseUrl: String,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
) : UsersNetworkDataSource {

    override fun searchUsers(
        searchUsersParams: SearchUsersRequestParams,
        commonParams: VkCommonRequestParams
    ): Flow<Result<List<SearchUserResponse>>> = flow {
        Timber.d("Sending users.search with $searchUsersParams")
        emit(Result.Loading)

        /*if ((1..6).random() == 1) { // todo remove later
            throw testFloodException
        }*/

        val httpResponse = httpClient.get("$baseUrl/users.search") {
            parameter("country", searchUsersParams.countryId)
            parameter("city", searchUsersParams.cityId)
            parameter("age_from", searchUsersParams.ageFrom)
            parameter("age_to", searchUsersParams.ageTo)
            parameter("birth_day", searchUsersParams.birthDay)
            parameter("birth_month", searchUsersParams.birthMonth)
            parameter("fields", searchUsersParams.fields)
            parameter("hometown", searchUsersParams.homeTown)
            parameter("status", searchUsersParams.relationId)
            parameter("sex", searchUsersParams.genderId)
            parameter("has_photo", if (searchUsersParams.hasPhoto) 1 else 0)
            parameter("count", searchUsersParams.count)
            parameter("sort", searchUsersParams.sortTypeId)
            parameter("access_token", commonParams.accessToken)
            parameter("v", commonParams.apiVersion)
            parameter("lang", commonParams.responseLanguage)
        }

        val responseBody = httpResponse.body<SearchUsersResponse>()

        if (httpResponse.isSuccessful && responseBody.error == null) {
            emit(Result.Success(responseBody.response?.searchUserResponseList.orEmpty()))
        } else {
            throw NetworkException.VkException(
                message = responseBody.error?.errorMessage,
                vkErrorCode = responseBody.error?.errorCode
            )
        }
    }.retryWhenFloodError(
        delayDuration = errorDelay.toDuration(),
        retryAttemptsCount = RETRY_ATTEMPTS_COUNT
    ).catch {
        emit(Result.Error(NetworkException.VkException(it)))
    }.flowOn(ioDispatcher)

    override fun getUser(
        getUserParams: GetUserRequestParams,
        commonParams: VkCommonRequestParams
    ): Flow<Result<UserResponse>> = flow {
        Timber.d("Sending users.get with $getUserParams")
        emit(Result.Loading)

        val httpResponse = httpClient.get("$baseUrl/users.get") {
            parameter("user_ids", getUserParams.userId)
            parameter("fields", getUserParams.fields)
            parameter("access_token", commonParams.accessToken)
            parameter("v", commonParams.apiVersion)
            parameter("lang", commonParams.responseLanguage)
        }

        val responseBody = httpResponse.body<GetUserResponse>()
        val userResponse = responseBody.userResponseList?.firstOrNull()

        if (httpResponse.isSuccessful && responseBody.error == null && userResponse != null) {
            emit(Result.Success(userResponse))
        } else {
            throw NetworkException.VkException(
                message = responseBody.error?.errorMessage,
                vkErrorCode = responseBody.error?.errorCode
            )
        }
    }.retryWhenFloodError(
        delayDuration = errorDelay.toDuration(),
        retryAttemptsCount = RETRY_ATTEMPTS_COUNT
    ).catch {
        emit(Result.Error(NetworkException.VkException(it)))
    }.flowOn(ioDispatcher)

    private companion object {

        private val errorDelay: Seconds = Seconds(15)
        private const val RETRY_ATTEMPTS_COUNT: Long = 3L
    }
}