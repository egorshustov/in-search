package com.egorshustov.vpoiske.core.domain.di

import com.egorshustov.vpoiske.core.domain.search.ProcessSearchInteractor
import com.egorshustov.vpoiske.core.domain.search.ProcessSearchInteractorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface DomainModule {

    @Binds
    fun bindsProcessSearchInteractor(
        processSearchInteractorImpl: ProcessSearchInteractorImpl
    ): ProcessSearchInteractor
}