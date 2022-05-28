package com.egorshustov.vpoiske.core.database.dao

import androidx.room.*
import com.egorshustov.vpoiske.core.database.model.CountryEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface CountryDao {

    @Query("select * from countries")
    fun getCountriesStream(): Flow<List<CountryEntity>>

    @Transaction
    suspend fun upsertCountries(entities: List<CountryEntity>) = upsertMany(
        items = entities,
        insertMany = ::insertOrIgnoreCountries,
        updateMany = ::updateCountries
    )

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnoreCountries(entities: List<CountryEntity>): List<Long>

    @Update
    suspend fun updateCountries(entities: List<CountryEntity>)
}