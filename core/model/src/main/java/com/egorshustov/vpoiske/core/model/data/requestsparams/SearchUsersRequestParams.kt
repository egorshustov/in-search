package com.egorshustov.vpoiske.core.model.data.requestsparams

const val SEARCH_USERS_FRIENDS_LIMIT_SET_FIELDS = "last_seen,contacts,followers_count,city,country"
const val SEARCH_USERS_FRIENDS_LIMIT_NOT_SET_FIELDS =
    "last_seen,contacts,followers_count,photo_id,sex,bdate,city,country,home_town,photo_50,photo_max,photo_max_orig,contacts,relation,can_write_private_message,can_send_friend_request"
const val MAX_POSSIBLE_USERS_COUNT = 1000

data class SearchUsersRequestParams(
    val countryId: Int,
    val cityId: Int,
    val ageFrom: Int?,
    val ageTo: Int?,
    val birthDay: Int,
    val birthMonth: Int,
    val fields: String,
    val homeTown: String?,
    val relationId: Int?,
    val genderId: Int,
    val hasPhoto: Boolean,
    val count: Int,
    val sortTypeId: Int
)