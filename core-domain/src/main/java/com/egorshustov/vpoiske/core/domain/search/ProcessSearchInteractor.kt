package com.egorshustov.vpoiske.core.domain.search

interface ProcessSearchInteractor {

    suspend fun startSearch(searchId: Long)
}