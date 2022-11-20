package com.egorshustov.insearch.core.database.di

import android.content.Context
import androidx.room.Room
import com.egorshustov.insearch.core.database.AppDatabase
import com.egorshustov.insearch.core.database.dao.CountryDao
import com.egorshustov.insearch.core.database.dao.SearchDao
import com.egorshustov.insearch.core.database.dao.UserDao
import com.egorshustov.insearch.core.database.datasource.*
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
    fun bindUsersDatabaseDataSource(
        usersRoomDataSource: UsersRoomDataSource
    ): UsersDatabaseDataSource

    @Binds
    fun bindSearchesDatabaseDataSource(
        searchesRoomDataSource: SearchesRoomDataSource
    ): SearchesDatabaseDataSource

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
                "InSearch.db"
            ).fallbackToDestructiveMigration().build() // TODO: check that fallback is working fine

        @Singleton
        @Provides
        internal fun provideUserDao(appDatabase: AppDatabase): UserDao =
            appDatabase.userDao()

        @Singleton
        @Provides
        internal fun provideSearchDao(appDatabase: AppDatabase): SearchDao =
            appDatabase.searchDao()

        @Singleton
        @Provides
        internal fun provideCountryDao(appDatabase: AppDatabase): CountryDao =
            appDatabase.countryDao()
    }
}
