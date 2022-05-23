package com.egorshustov.vpoiske.core.data.di

import com.egorshustov.vpoiske.core.data.repository.DefaultUsersRepository
import com.egorshustov.vpoiske.core.data.repository.UsersRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface DataModule {

    @Binds
    fun bindsTopicRepository(
        defaultUsersRepository: DefaultUsersRepository
    ): UsersRepository
}