package com.egorshustov.core.di

import com.egorshustov.auth.api.AuthFeatureApi
import com.egorshustov.auth.impl.AuthFeatureImpl
import com.egorshustov.search.api.SearchFeatureApi
import com.egorshustov.search.impl.SearchFeatureImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FeaturesModule {

    @Singleton
    @Provides
    fun provideSearchFeatureApi(): SearchFeatureApi = SearchFeatureImpl()

    @Singleton
    @Provides
    fun provideAuthFeatureApi(): AuthFeatureApi = AuthFeatureImpl()

    @Named("SearchRoute")
    @Singleton
    @Provides
    fun provideSearchFeatureRoute(searchFeatureApi: SearchFeatureApi): String =
        searchFeatureApi.searchGraphRoute()

    @Named("AuthRoute")
    @Singleton
    @Provides
    fun provideAuthFeatureRoute(authFeatureApi: AuthFeatureApi): String =
        authFeatureApi.authGraphRoute()
}