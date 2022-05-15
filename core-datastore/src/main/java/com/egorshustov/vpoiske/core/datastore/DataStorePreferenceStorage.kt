package com.egorshustov.vpoiske.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.egorshustov.vpoiske.core.common.model.Theme
import com.egorshustov.vpoiske.core.datastore.DataStorePreferenceStorage.PreferencesKeys.PREF_KEY_ACCESS_TOKEN
import com.egorshustov.vpoiske.core.datastore.DataStorePreferenceStorage.PreferencesKeys.PREF_KEY_SELECTED_COLUMN_COUNT
import com.egorshustov.vpoiske.core.datastore.DataStorePreferenceStorage.PreferencesKeys.PREF_KEY_SELECTED_THEME
import com.egorshustov.core.common.utils.MAX_COLUMN_COUNT
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStorePreferenceStorage @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : PreferenceStorage {

    companion object {
        const val PREFS_NAME = "com.egorshustov.vpoiskepreferences"
    }

    object PreferencesKeys {
        val PREF_KEY_ACCESS_TOKEN =
            stringPreferencesKey("com.egorshustov.vpoiske.PREF_KEY_ACCESS_TOKEN")
        val PREF_KEY_SELECTED_THEME =
            stringPreferencesKey("com.egorshustov.vpoiske.PREF_KEY_CURRENT_THEME")
        val PREF_KEY_SELECTED_COLUMN_COUNT =
            intPreferencesKey("com.egorshustov.vpoiske.PREF_KEY_SELECTED_COLUMN_COUNT")
    }

    override suspend fun saveAccessToken(token: String) {
        dataStore.edit {
            it[PREF_KEY_ACCESS_TOKEN] = token
        }
    }

    override val accessToken: Flow<String>
        get() = dataStore.data.map { it[PREF_KEY_ACCESS_TOKEN].orEmpty() }

    override suspend fun selectTheme(theme: String) {
        dataStore.edit {
            it[PREF_KEY_SELECTED_THEME] = theme
        }
    }

    override val selectedTheme =
        dataStore.data.map { it[PREF_KEY_SELECTED_THEME] ?: Theme.SYSTEM.storageKey }

    override suspend fun selectColumnCount(count: Int) {
        dataStore.edit {
            it[PREF_KEY_SELECTED_COLUMN_COUNT] = count
        }
    }

    override val selectedColumnCount: Flow<Int> =
        dataStore.data.map { it[PREF_KEY_SELECTED_COLUMN_COUNT] ?: MAX_COLUMN_COUNT }
}