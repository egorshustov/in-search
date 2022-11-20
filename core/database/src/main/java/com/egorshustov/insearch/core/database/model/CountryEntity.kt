package com.egorshustov.insearch.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.egorshustov.insearch.core.model.data.Country

@Entity(tableName = "countries")
data class CountryEntity(

    @PrimaryKey
    val id: Int,

    val title: String
)

fun CountryEntity.asExternalModel() = Country(
    id = id,
    title = title
)

fun List<CountryEntity>.asExternalModelList(): List<Country> = map { it.asExternalModel() }