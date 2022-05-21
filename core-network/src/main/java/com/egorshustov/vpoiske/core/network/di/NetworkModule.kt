package com.egorshustov.vpoiske.core.network.di

import com.egorshustov.vpoiske.core.network.AppBaseUrl
import com.egorshustov.vpoiske.core.network.VkUrlConfig
import com.egorshustov.vpoiske.core.network.datasource.UsersKtorDataSource
import com.egorshustov.vpoiske.core.network.datasource.UsersNetworkDataSource
import com.egorshustov.vpoiske.core.network.ktor.KtorClientFactory
import com.egorshustov.vpoiske.core.network.ktor.KtorClientFactoryAndroidImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface NetworkModule {

    @Binds
    fun bindUsersNetworkDataSource(
        usersKtorDataSource: UsersKtorDataSource
    ): UsersNetworkDataSource

    companion object {

        @Singleton
        @Provides
        internal fun provideKtorClientFactory(): KtorClientFactory = KtorClientFactoryAndroidImpl()

        @Singleton
        @Provides
        internal fun provideHttpClient(ktorClientFactory: KtorClientFactory): HttpClient =
            ktorClientFactory.build()

        @Singleton
        @Provides
        @AppBaseUrl
        internal fun provideBaseUrl(): String = VkUrlConfig.BASE_URL
    }
}