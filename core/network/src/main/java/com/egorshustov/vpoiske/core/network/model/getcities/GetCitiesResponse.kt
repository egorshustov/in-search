package com.egorshustov.vpoiske.core.network.model.getcities

import com.egorshustov.vpoiske.core.network.model.BaseVkResponse
import com.egorshustov.vpoiske.core.network.model.VkErrorResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetCitiesResponse(

    @SerialName("response")
    val response: GetCitiesInnerResponse? = null,

    @SerialName("error")
    override val error: VkErrorResponse? = null
) : BaseVkResponse()