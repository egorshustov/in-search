package com.egorshustov.vpoiske.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchUsersInnerResponse(

    @SerialName("count")
    val count: Int?,

    @SerialName("items")
    val searchUserResponseList: List<SearchUserResponse>?
)