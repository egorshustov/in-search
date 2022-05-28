package com.egorshustov.vpoiske.core.database.di

import android.content.Context
import androidx.room.Room
import com.egorshustov.vpoiske.core.database.AppDatabase
import com.egorshustov.vpoiske.core.database.dao.CountryDao
import com.egorshustov.vpoiske.core.database.datasource.CountriesDatabaseDataSource
import com.egorshustov.vpoiske.core.database.datasource.CountriesRoomDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface DatabaseModule {

    @Binds
    fun bindCountriesDatabaseDataSource(
        countriesRoomDataSource: CountriesRoomDataSource
    ): CountriesDatabaseDataSource

    companion object {

        @Singleton
        @Provides
        internal fun providesAppDatabase(@ApplicationContext context: Context): AppDatabase =
            Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "VPoiske.db"
            ).build()

        @Singleton
        @Provides
        internal fun provideCountryDao(appDatabase: AppDatabase): CountryDao =
            appDatabase.countryDao()
    }
}
