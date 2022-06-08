package com.egorshustov.vpoiske.core.model.data.requestsparams

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