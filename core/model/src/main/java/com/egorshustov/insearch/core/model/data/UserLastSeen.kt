package com.egorshustov.insearch.core.model.data

import com.egorshustov.insearch.core.common.utils.UnixSeconds

data class UserLastSeen(
    val time: UnixSeconds? = null,
    val platformId: Int? = null
)