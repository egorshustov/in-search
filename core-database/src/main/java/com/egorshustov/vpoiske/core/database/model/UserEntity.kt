package com.egorshustov.vpoiske.core.database.model

import androidx.room.*
import com.egorshustov.vpoiske.core.common.utils.UnixMillis
import com.egorshustov.vpoiske.core.model.data.*
import com.egorshustov.vpoiske.core.model.data.Relation

@Entity(
    tableName = "users",
    foreignKeys = [
        ForeignKey(
            entity = SearchEntity::class,
            parentColumns = ["id"],
            childColumns = ["search_id"],
            onDelete = ForeignKey.CASCADE
        ),
    ]
)
data class UserEntity(

    @PrimaryKey
    val id: Long,

    @ColumnInfo(name = "first_name")
    val firstName: String,

    @ColumnInfo(name = "last_name")
    val lastName: String,

    val gender: Gender?,

    @ColumnInfo(name = "birth_date")
    val birthDate: String,

    @Embedded(prefix = "city_")
    val city: CityEmbedded,

    @Embedded(prefix = "country_")
    val country: CountryEmbedded,

    @ColumnInfo(name = "home_town")
    val homeTown: String,

    @Embedded
    val photosInfo: UserPhotosInfoEmbedded,

    @ColumnInfo(name = "mobile_phone")
    val mobilePhone: String,

    @ColumnInfo(name = "home_phone")
    val homePhone: String,

    val relation: Relation?,

    @Embedded
    val counters: UserCountersEmbedded,

    @Embedded
    val permissions: UserPermissionsEmbedded,

    @ColumnInfo(name = "search_id")
    val searchId: Long,

    @ColumnInfo(name = "found_unix_millis")
    val foundUnixMillis: Long
)

fun UserEntity.asExternalModel() = User(
    id = id,
    firstName = firstName,
    lastName = lastName,
    gender = gender,
    birthDate = birthDate,
    city = city.id?.let { City(it, city.title, city.area, city.region) },
    country = country.id?.let { Country(it, country.title) },
    homeTown = homeTown,
    photosInfo = photosInfo.asExternalModel(),
    mobilePhone = mobilePhone,
    homePhone = homePhone,
    relation = relation,
    lastSeen = null,
    counters = counters.asExternalModel(),
    permissions = permissions.asExternalModel(),
    searchId = searchId,
    foundTime = UnixMillis(foundUnixMillis)
)