package com.egorshustov.vpoiske.core.database.datasource

import androidx.paging.PagingSource
import com.egorshustov.vpoiske.core.common.model.Result
import com.egorshustov.vpoiske.core.database.model.SearchEntity
import com.egorshustov.vpoiske.core.database.model.SearchWithUsersPopulated
import kotlinx.coroutines.flow.Flow

interface SearchesDatabaseDataSource {

    fun getSearchesWithUsers(): PagingSource<Int, SearchWithUsersPopulated>

    fun getLastSearchIdStream(): Flow<Long?>

    suspend fun getSearch(id: Long): Result<SearchEntity>

    suspend fun saveSearch(entity: SearchEntity): Result<Long>

    suspend fun deleteSearch(id: Long)
}