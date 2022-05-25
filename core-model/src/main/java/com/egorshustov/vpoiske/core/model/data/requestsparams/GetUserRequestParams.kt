package com.egorshustov.vpoiske.core.model.data.requestsparams

data class GetUserRequestParams(
    val userId: Long,
    val fields: String
)