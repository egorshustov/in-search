package com.egorshustov.vpoiske.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.egorshustov.vpoiske.core.database.dao.CountryDao
import com.egorshustov.vpoiske.core.database.dao.UserDao
import com.egorshustov.vpoiske.core.database.model.CountryEntity
import com.egorshustov.vpoiske.core.database.model.SearchEntity
import com.egorshustov.vpoiske.core.database.model.UserEntity

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

)
internal abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun countryDao(): CountryDao
}
