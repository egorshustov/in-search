package com.egorshustov.insearch.core.model.data.requestsparams

const val GET_USER_FIELDS =
    "photo_id,sex,bdate,city,country,home_town,counters,photo_50,photo_max,photo_max_orig,contacts,relation,can_write_private_message,can_send_friend_request"

data class GetUserRequestParams(
    val userId: Long,
    val fields: String
)