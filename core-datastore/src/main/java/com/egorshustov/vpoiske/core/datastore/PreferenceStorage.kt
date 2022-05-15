package com.egorshustov.vpoiske.core.datastore

import kotlinx.coroutines.flow.Flow

/**
 * Storage for app and user preferences.
 */
interface PreferenceStorage {

    suspend fun saveAccessToken(token: String)
    val accessToken: Flow<String>

    suspend fun selectTheme(theme: String)
    val selectedTheme: Flow<String>

    suspend fun selectColumnCount(count: Int)
    val selectedColumnCount: Flow<Int>
}