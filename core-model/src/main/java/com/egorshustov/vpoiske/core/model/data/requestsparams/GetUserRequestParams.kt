package com.egorshustov.vpoiske.core.model.data.requestsparams

const val DEFAULT_GET_USER_FIELDS =
    "photo_id,sex,bdate,city,country,home_town,counters,photo_50,photo_max,photo_max_orig,contacts,relation,can_write_private_message,can_send_friend_request"

data class GetUserRequestParams(
    val userId: Long,
    val fields: String
)