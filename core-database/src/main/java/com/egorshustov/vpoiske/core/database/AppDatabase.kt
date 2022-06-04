package com.egorshustov.vpoiske.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.egorshustov.vpoiske.core.database.dao.CountryDao
import com.egorshustov.vpoiske.core.database.dao.UserDao
import com.egorshustov.vpoiske.core.database.model.CountryEntity
import com.egorshustov.vpoiske.core.database.model.UserEntity

@Database(
    entities = [UserEntity::class, CountryEntity::class],
    version = 1,
    exportSchema = false
)
// TODO: add migrations from old app, increase DB version
//  and make sure that update is working seamlessly
internal abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun countryDao(): CountryDao
}
