package com.egorshustov.vpoiske.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetUserResponse(

    @SerialName("response")
    val userResponseList: List<UserResponse>? = null,

    @SerialName("error")
    override val error: VkErrorResponse? = null
) : BaseVkResponse()