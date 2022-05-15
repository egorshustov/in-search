package com.egorshustov.vpoiske.core.network.di

import com.egorshustov.vpoiske.core.network.ktor.KtorClientFactory
import com.egorshustov.vpoiske.core.network.ktor.KtorClientFactoryAndroidImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    internal fun provideKtorClientFactory(): KtorClientFactory = KtorClientFactoryAndroidImpl()

    @Singleton
    @Provides
    internal fun provideHttpClient(ktorClientFactory: KtorClientFactory): HttpClient =
        ktorClientFactory.build()
}