package com.egorshustov.vpoiske.core.model.data

import com.egorshustov.vpoiske.core.common.utils.UnixSeconds

data class UserLastSeen(
    val time: UnixSeconds? = null,
    val platformId: Int? = null
)