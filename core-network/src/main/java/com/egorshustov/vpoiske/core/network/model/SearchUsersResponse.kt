package com.egorshustov.vpoiske.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchUsersResponse(

    @SerialName("response")
    val response: SearchUsersInnerResponse? = null,

    @SerialName("error")
    override val error: VkErrorResponse? = null
) : BaseVkResponse()