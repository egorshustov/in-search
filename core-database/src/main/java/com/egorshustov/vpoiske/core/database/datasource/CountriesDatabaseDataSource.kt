package com.egorshustov.vpoiske.core.database.datasource

import com.egorshustov.vpoiske.core.database.model.CountryEntity
import kotlinx.coroutines.flow.Flow

interface CountriesDatabaseDataSource {

    fun getCountriesStream(): Flow<List<CountryEntity>>

    suspend fun upsertCountries(entities: List<CountryEntity>)
}