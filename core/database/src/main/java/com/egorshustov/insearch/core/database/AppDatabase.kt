package com.egorshustov.insearch.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.egorshustov.insearch.core.database.dao.CountryDao
import com.egorshustov.insearch.core.database.dao.SearchDao
import com.egorshustov.insearch.core.database.dao.UserDao
import com.egorshustov.insearch.core.database.model.CountryEntity
import com.egorshustov.insearch.core.database.model.SearchEntity
import com.egorshustov.insearch.core.database.model.UserEntity
import com.egorshustov.insearch.core.database.util.GenderConverter
import com.egorshustov.insearch.core.database.util.RelationConverter

@Database(
    entities = [
        UserEntity::class,
        SearchEntity::class,
        CountryEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    GenderConverter::class,
    RelationConverter::class
)
internal abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun searchDao(): SearchDao
    abstract fun countryDao(): CountryDao
}
