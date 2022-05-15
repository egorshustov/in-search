package com.egorshustov.auth.impl.di

import com.egorshustov.auth.api.AuthFeatureApi
import com.egorshustov.auth.impl.AuthFeatureImpl
import com.egorshustov.core.common.utils.DI_NAME_AUTH_ROUTE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthFeatureModule {

    @Singleton
    @Provides
    fun provideAuthFeatureApi(): AuthFeatureApi = AuthFeatureImpl()

    @Named(DI_NAME_AUTH_ROUTE)
    @Singleton
    @Provides
    fun provideAuthRoute(authFeatureApi: AuthFeatureApi): String =
        authFeatureApi.authRoute()
}