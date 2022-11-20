package com.egorshustov.insearch.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.egorshustov.insearch.core.common.utils.MAX_COLUMN_COUNT
import com.egorshustov.insearch.core.datastore.DataStorePreferenceStorage.PreferencesKeys.PREF_KEY_ACCESS_TOKEN
import com.egorshustov.insearch.core.datastore.DataStorePreferenceStorage.PreferencesKeys.PREF_KEY_SELECTED_COLUMN_COUNT
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class DataStorePreferenceStorage @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : PreferenceStorage {

    companion object {
        const val PREFS_NAME = "com.egorshustov.insearchpreferences"
    }

    object PreferencesKeys {
        val PREF_KEY_ACCESS_TOKEN =
            stringPreferencesKey("com.egorshustov.insearch.PREF_KEY_ACCESS_TOKEN")
        val PREF_KEY_SELECTED_COLUMN_COUNT =
            intPreferencesKey("com.egorshustov.insearch.PREF_KEY_SELECTED_COLUMN_COUNT")
    }

    override suspend fun saveAccessToken(token: String) {
        dataStore.edit {
            it[PREF_KEY_ACCESS_TOKEN] = token
        }
    }

    override val accessToken: Flow<String>
        get() = dataStore.data.map { it[PREF_KEY_ACCESS_TOKEN].orEmpty() }

    override suspend fun saveSelectedColumnCount(count: Int) {
        dataStore.edit {
            it[PREF_KEY_SELECTED_COLUMN_COUNT] = count
        }
    }

    override val selectedColumnCount: Flow<Int> =
        dataStore.data.map { it[PREF_KEY_SELECTED_COLUMN_COUNT] ?: MAX_COLUMN_COUNT }
}