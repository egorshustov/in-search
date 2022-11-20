package com.egorshustov.insearch.core.model.data

import com.egorshustov.insearch.core.common.utils.UrlString

data class SearchWithUsersPhotos(
    val search: Search,
    val photos: List<UrlString>
)