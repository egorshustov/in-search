package com.egorshustov.vpoiske.core.network.ktor

import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

internal class KtorClientFactoryAndroidImpl : KtorClientFactory {

    override fun build(): HttpClient = HttpClient(Android) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true // if the server sends extra fields, ignore
                explicitNulls = false
            })
        }
        install(HttpTimeout) {
            connectTimeoutMillis = 3000
        }
    }
}