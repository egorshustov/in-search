package com.egorshustov.vpoiske.core.network.model.getuser

import com.egorshustov.vpoiske.core.network.model.BaseVkResponse
import com.egorshustov.vpoiske.core.network.model.VkErrorResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetUserResponse(

    @SerialName("response")
    val userResponseList: List<UserResponse>? = null,

    @SerialName("error")
    override val error: VkErrorResponse? = null
) : BaseVkResponse()