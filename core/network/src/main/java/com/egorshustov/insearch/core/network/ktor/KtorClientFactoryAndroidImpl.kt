package com.egorshustov.insearch.core.network.ktor

import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import timber.log.Timber

internal class KtorClientFactoryAndroidImpl : KtorClientFactory {

    override fun build(): HttpClient = HttpClient(Android) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true // if the server sends extra fields, ignore
            })
        }
        install(HttpTimeout) {
            connectTimeoutMillis = 3000
        }
        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Timber.d(message)
                }
            }
            level = LogLevel.NONE
        }
    }
}