package com.egorshustov.vpoiske.core.network.datasource

import com.egorshustov.vpoiske.core.common.exceptions.NetworkException
import com.egorshustov.vpoiske.core.common.model.Result
import com.egorshustov.vpoiske.core.common.network.AppDispatchers.IO
import com.egorshustov.vpoiske.core.common.network.Dispatcher
import com.egorshustov.vpoiske.core.model.data.requestsparams.GetCountriesRequestParams
import com.egorshustov.vpoiske.core.model.data.requestsparams.VkCommonRequestParams
import com.egorshustov.vpoiske.core.network.AppBaseUrl
import com.egorshustov.vpoiske.core.network.isSuccessful
import com.egorshustov.vpoiske.core.network.model.getcountries.CountryResponse
import com.egorshustov.vpoiske.core.network.model.getcountries.GetCountriesResponse
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
internal class CountriesKtorDataSource @Inject constructor(
    private val httpClient: HttpClient,
    @AppBaseUrl private val baseUrl: String,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
) : CountriesNetworkDataSource {

    override fun getCountries(
        getCountriesParams: GetCountriesRequestParams,
        commonParams: VkCommonRequestParams
    ): Flow<Result<List<CountryResponse>>> = flow {
        emit(Result.Loading)

        try {
            val httpResponse = httpClient.get("$baseUrl/database.getCountries") {
                parameter("need_all", if (getCountriesParams.needAll) 1 else 0)
                parameter("count", getCountriesParams.count)
                parameter("access_token", commonParams.accessToken)
                parameter("v", commonParams.apiVersion)
                parameter("lang", commonParams.responseLanguage)
            }

            val responseBody = httpResponse.body<GetCountriesResponse>()

            if (httpResponse.isSuccessful && responseBody.error == null) {
                emit(Result.Success(responseBody.response?.countryResponseList.orEmpty()))
            } else {
                emit(
                    Result.Error(
                        NetworkException.VkException(
                            message = responseBody.error?.errorMessage,
                            vkErrorCode = responseBody.error?.errorCode
                        )
                    )
                )
            }
        } catch (e: Throwable) {
            emit(Result.Error(NetworkException.VkException(e)))
        }
    }.flowOn(ioDispatcher)
}