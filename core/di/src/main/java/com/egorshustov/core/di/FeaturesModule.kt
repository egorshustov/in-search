package com.egorshustov.core.di

import com.egorshustov.auth.api.AuthFeatureApi
import com.egorshustov.auth.impl.AuthFeatureImpl
import com.egorshustov.core.common.utils.DI_NAME_AUTH_ROUTE
import com.egorshustov.core.common.utils.DI_NAME_SEARCH_ROUTE
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
    fun provideAuthFeatureApi(): AuthFeatureApi = AuthFeatureImpl()

    @Singleton
    @Provides
    fun provideSearchFeatureApi(): SearchFeatureApi = SearchFeatureImpl()

    @Named(DI_NAME_AUTH_ROUTE)
    @Singleton
    @Provides
    fun provideAuthRoute(authFeatureApi: AuthFeatureApi): String =
        authFeatureApi.authRoute()

    @Named(DI_NAME_SEARCH_ROUTE)
    @Singleton
    @Provides
    fun provideSearchRoute(searchFeatureApi: SearchFeatureApi): String =
        searchFeatureApi.searchRoute()
}