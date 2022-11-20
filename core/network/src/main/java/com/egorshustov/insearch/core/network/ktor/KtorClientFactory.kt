package com.egorshustov.insearch.core.network.ktor

import io.ktor.client.*

internal interface KtorClientFactory {

    fun build(): HttpClient
}