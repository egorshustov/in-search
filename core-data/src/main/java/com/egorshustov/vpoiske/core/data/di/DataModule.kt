package com.egorshustov.vpoiske.core.data.di

import com.egorshustov.vpoiske.core.data.repository.*
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

    @Binds
    fun bindsCitiesRepository(
        defaultCitiesRepository: DefaultCitiesRepository
    ): CitiesRepository
}