package com.egorshustov.insearch.core.network.model.searchuser

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchUsersInnerResponse(

    @SerialName("count")
    val count: Int? = null,

    @SerialName("items")
    val searchUserResponseList: List<SearchUserResponse>? = null
)