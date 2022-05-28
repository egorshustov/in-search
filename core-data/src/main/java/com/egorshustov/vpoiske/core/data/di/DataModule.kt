package com.egorshustov.vpoiske.core.data.di

import com.egorshustov.vpoiske.core.data.repository.CountriesRepository
import com.egorshustov.vpoiske.core.data.repository.DefaultCountriesRepository
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
    fun bindsUsersRepository(
        defaultUsersRepository: DefaultUsersRepository
    ): UsersRepository

    @Binds
    fun bindsCountriesRepository(
        defaultCountriesRepository: DefaultCountriesRepository
    ): CountriesRepository
}