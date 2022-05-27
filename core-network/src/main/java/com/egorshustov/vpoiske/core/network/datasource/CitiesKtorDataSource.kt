package com.egorshustov.vpoiske.core.network.datasource

import com.egorshustov.vpoiske.core.common.model.AppException
import com.egorshustov.vpoiske.core.common.model.Result
import com.egorshustov.vpoiske.core.common.network.AppDispatchers.IO
import com.egorshustov.vpoiske.core.common.network.Dispatcher
import com.egorshustov.vpoiske.core.model.data.requestsparams.GetCitiesRequestParams
import com.egorshustov.vpoiske.core.model.data.requestsparams.VkCommonRequestParams
import com.egorshustov.vpoiske.core.network.AppBaseUrl
import com.egorshustov.vpoiske.core.network.ktor.isSuccessful
import com.egorshustov.vpoiske.core.network.model.getcities.CityResponse
import com.egorshustov.vpoiske.core.network.model.getcities.GetCitiesResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class CitiesKtorDataSource @Inject constructor(
    private val httpClient: HttpClient,
    @AppBaseUrl private val baseUrl: String,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
) : CitiesNetworkDataSource {

    override fun getCities(
        getCitiesParams: GetCitiesRequestParams,
        commonParams: VkCommonRequestParams
    ): Flow<Result<List<CityResponse>>> = flow {
        emit(Result.Loading)

        try {
            val httpResponse = httpClient.get("$baseUrl/database.getCities") {
                parameter("country_id", getCitiesParams.countryId)
                parameter("need_all", if (getCitiesParams.needAll) 1 else 0)
                parameter("q", getCitiesParams.searchQuery)
                parameter("count", getCitiesParams.count)
                parameter("access_token", commonParams.accessToken)
                parameter("v", commonParams.apiVersion)
                parameter("lang", commonParams.responseLanguage)
            }

            val responseBody = httpResponse.body<GetCitiesResponse>()

            if (httpResponse.isSuccessful && responseBody.error == null) {
                emit(Result.Success(responseBody.response?.cityResponseList.orEmpty()))
            } else {
                emit(
                    Result.Error(
                        AppException(
                            message = responseBody.error?.errorMessage,
                            vkErrorCode = responseBody.error?.errorCode
                        )
                    )
                )
            }
        } catch (e: Throwable) {
            emit(Result.Error(AppException(e)))
        }
    }.flowOn(ioDispatcher)
}