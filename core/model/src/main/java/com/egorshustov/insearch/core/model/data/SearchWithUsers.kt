package com.egorshustov.insearch.core.model.data

data class SearchWithUsers(
    val search: Search,
    val users: List<User>
)