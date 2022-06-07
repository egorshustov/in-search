package com.egorshustov.vpoiske.core.database.di

import android.content.Context
import androidx.room.Room
import com.egorshustov.vpoiske.core.database.AppDatabase
import com.egorshustov.vpoiske.core.database.dao.CountryDao
import com.egorshustov.vpoiske.core.database.dao.UserDao
import com.egorshustov.vpoiske.core.database.datasource.CountriesDatabaseDataSource
import com.egorshustov.vpoiske.core.database.datasource.CountriesRoomDataSource
import com.egorshustov.vpoiske.core.database.datasource.UsersDatabaseDataSource
import com.egorshustov.vpoiske.core.database.datasource.UsersRoomDataSource
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
            ).fallbackToDestructiveMigration().build() // TODO: check that fallback is working fine

        @Singleton
        @Provides
        internal fun provideUserDao(appDatabase: AppDatabase): UserDao =
            appDatabase.userDao()

        @Singleton
        @Provides
        internal fun provideCountryDao(appDatabase: AppDatabase): CountryDao =
            appDatabase.countryDao()
    }
}
