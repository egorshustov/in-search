package com.egorshustov.vpoiske.core.common.network.di

import com.egorshustov.vpoiske.core.common.network.AppDispatchers.IO
import com.egorshustov.vpoiske.core.common.network.AppDispatchers.MAIN
import com.egorshustov.vpoiske.core.common.network.Dispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@InstallIn(SingletonComponent::class)
@Module
object DispatchersModule {

    @Provides
    @Dispatcher(MAIN)
    fun providesMainDispatcher(): CoroutineDispatcher = Dispatchers.Main.immediate

    @Provides
    @Dispatcher(IO)
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
}
