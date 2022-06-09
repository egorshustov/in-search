package com.egorshustov.vpoiske.core.database.datasource

import com.egorshustov.vpoiske.core.common.network.AppDispatchers.IO
import com.egorshustov.vpoiske.core.common.network.Dispatcher
import com.egorshustov.vpoiske.core.database.dao.SearchDao
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class SearchesRoomDataSource @Inject constructor(
    private val searchDao: SearchDao,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
) : SearchesDatabaseDataSource {
}