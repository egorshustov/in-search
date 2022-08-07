package com.egorshustov.vpoiske.feature.search.process_search.di

import com.egorshustov.vpoiske.feature.search.process_search.ProcessSearchPresenter
import com.egorshustov.vpoiske.feature.search.process_search.ProcessSearchPresenterImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface ProcessSearchModule {

    @Binds
    fun bindsProcessSearchPresenter(
        processSearchPresenterImpl: ProcessSearchPresenterImpl
    ): ProcessSearchPresenter
}