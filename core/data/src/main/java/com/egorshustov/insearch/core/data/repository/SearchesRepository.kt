package com.egorshustov.insearch.core.data.repository

import androidx.paging.PagingData
import com.egorshustov.insearch.core.common.model.Result
import com.egorshustov.insearch.core.model.data.Search
import com.egorshustov.insearch.core.model.data.SearchWithUsers
import com.egorshustov.insearch.core.model.data.requestsparams.PagingConfigParams
import kotlinx.coroutines.flow.Flow

interface SearchesRepository {

    fun getSearchesWithUsersStream(params: PagingConfigParams): Flow<PagingData<SearchWithUsers>>

    fun getLastSearchIdStream(): Flow<Long?>

    suspend fun getSearch(id: Long): Result<Search>

    suspend fun getLastSearch(): Result<Search>

    suspend fun saveSearch(search: Search): Result<Long>

    suspend fun deleteSearch(id: Long)
}