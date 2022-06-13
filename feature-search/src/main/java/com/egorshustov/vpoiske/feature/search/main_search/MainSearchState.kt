package com.egorshustov.vpoiske.feature.search.main_search

import com.egorshustov.vpoiske.core.model.data.User

internal data class MainSearchState(
    val users: List<User> = emptyList(),
    val isAuthRequired: Boolean = false
)