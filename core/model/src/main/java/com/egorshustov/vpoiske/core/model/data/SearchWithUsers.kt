package com.egorshustov.vpoiske.core.model.data

data class SearchWithUsers(
    val search: Search,
    val users: List<User>
)