package com.egorshustov.vpoiske.core.datastore

import kotlinx.coroutines.flow.Flow

/**
 * Storage for app and user preferences.
 */
interface PreferenceStorage {

    suspend fun saveAccessToken(token: String)
    val accessToken: Flow<String>

    suspend fun saveSelectedTheme(theme: String)
    val selectedTheme: Flow<String>

    suspend fun saveSelectedColumnCount(count: Int)
    val selectedColumnCount: Flow<Int>
}