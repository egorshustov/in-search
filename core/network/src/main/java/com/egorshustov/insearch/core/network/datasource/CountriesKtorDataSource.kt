package com.egorshustov.insearch.core.network.datasource

import com.egorshustov.insearch.core.common.exceptions.NetworkException
import com.egorshustov.insearch.core.common.model.Result
import com.egorshustov.insearch.core.common.network.AppDispatchers.IO
import com.egorshustov.insearch.core.common.network.Dispatcher
import com.egorshustov.insearch.core.model.data.requestsparams.GetCountriesRequestParams
import com.egorshustov.insearch.core.model.data.requestsparams.VkCommonRequestParams
import com.egorshustov.insearch.core.network.AppBaseUrl
import com.egorshustov.insearch.core.network.AppReserveUrl
import com.egorshustov.insearch.core.network.isSuccessful
import com.egorshustov.insearch.core.network.model.getcountries.CountryResponse
import com.egorshustov.insearch.core.network.model.getcountries.GetCountriesResponse
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
    @AppReserveUrl private val reserveUrl: String,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
) : CountriesNetworkDataSource {

    override fun getCountries(
        getCountriesParams: GetCountriesRequestParams,
        commonParams: VkCommonRequestParams
    ): Flow<Result<List<CountryResponse>>> = flow {
        emit(Result.Loading)

        try {
            var httpResponse = httpClient.get("$baseUrl/database.getCountries") {
                parameter("need_all", if (getCountriesParams.needAll) 1 else 0)
                parameter("count", getCountriesParams.count)
                parameter("access_token", commonParams.accessToken)
                parameter("v", commonParams.apiVersion)
                parameter("lang", commonParams.responseLanguage)
            }

            var responseBody = httpResponse.body<GetCountriesResponse>()

            if (responseBody.response?.countryResponseList.isNullOrEmpty()) {
                httpResponse = httpClient.get("$reserveUrl/getCountries.json")
                responseBody = httpResponse.body()
            }

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
            emit(Result.Error(e))
        }
    }.flowOn(ioDispatcher)
}