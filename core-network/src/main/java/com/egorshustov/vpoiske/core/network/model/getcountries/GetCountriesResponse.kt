package com.egorshustov.vpoiske.core.network.model.getcountries

import com.egorshustov.vpoiske.core.network.model.BaseVkResponse
import com.egorshustov.vpoiske.core.network.model.VkErrorResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetCountriesResponse(

    @SerialName("response")
    val response: GetCountriesInnerResponse? = null,

    @SerialName("error")
    override val error: VkErrorResponse? = null
) : BaseVkResponse()