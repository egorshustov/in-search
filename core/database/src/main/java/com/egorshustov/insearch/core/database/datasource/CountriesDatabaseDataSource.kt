package com.egorshustov.insearch.core.database.datasource

import com.egorshustov.insearch.core.database.model.CountryEntity
import kotlinx.coroutines.flow.Flow

interface CountriesDatabaseDataSource {

    fun getCountriesStream(): Flow<List<CountryEntity>>

    suspend fun upsertCountries(entities: List<CountryEntity>)
}