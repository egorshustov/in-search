package com.egorshustov.vpoiske.core.model.data

import com.egorshustov.vpoiske.core.common.utils.UrlString

data class SearchWithUsersPhotos(
    val search: Search,
    val photos: List<UrlString>
)