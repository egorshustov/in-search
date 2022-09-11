package com.egorshustov.vpoiske.core.datastore.di

import android.content.Context
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.preferencesDataStore
import com.egorshustov.vpoiske.core.datastore.DataStorePreferenceStorage
import com.egorshustov.vpoiske.core.datastore.PreferenceStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal object DataStoreModule {

    private val Context.dataStore by preferencesDataStore(
        name = DataStorePreferenceStorage.PREFS_NAME,
        produceMigrations = { context ->
            listOf(
                SharedPreferencesMigration(
                    context,
                    DataStorePreferenceStorage.PREFS_NAME
                )
            )
        }
    )

    @Singleton
    @Provides
    internal fun providePreferenceStorage(@ApplicationContext context: Context): PreferenceStorage =
        DataStorePreferenceStorage(context.dataStore)
}
