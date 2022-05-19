package com.egorshustov.vpoiske.core.model.data

data class SearchUsersRequestParams(
    val countryId: Int,
    val cityId: Int,
    val ageFrom: Int?,
    val ageTo: Int?,
    val birthDay: Int,
    val birthMonth: Int,
    val fields: String,
    val homeTown: String?,
    val relation: Int?,
    val sex: Int,
    val hasPhoto: Int,
    val count: Int,
    val sortType: Int
)