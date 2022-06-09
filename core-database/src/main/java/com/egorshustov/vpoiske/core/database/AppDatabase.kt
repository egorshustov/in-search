package com.egorshustov.vpoiske.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.egorshustov.vpoiske.core.database.dao.CountryDao
import com.egorshustov.vpoiske.core.database.dao.UserDao
import com.egorshustov.vpoiske.core.database.model.CountryEntity
import com.egorshustov.vpoiske.core.database.model.SearchEntity
import com.egorshustov.vpoiske.core.database.model.UserEntity
import com.egorshustov.vpoiske.core.database.util.GenderConverter
import com.egorshustov.vpoiske.core.database.util.RelationConverter

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
    abstract fun countryDao(): CountryDao
}
