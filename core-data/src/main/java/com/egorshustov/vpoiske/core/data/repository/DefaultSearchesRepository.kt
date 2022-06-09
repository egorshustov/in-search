package com.egorshustov.vpoiske.core.data.repository

import com.egorshustov.vpoiske.core.common.network.AppDispatchers
import com.egorshustov.vpoiske.core.common.network.Dispatcher
import com.egorshustov.vpoiske.core.database.datasource.SearchesDatabaseDataSource
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class DefaultSearchesRepository @Inject constructor(
    private val searchesDatabaseDataSource: SearchesDatabaseDataSource,
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : SearchesRepository {
}