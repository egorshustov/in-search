package com.egorshustov.insearch.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.egorshustov.insearch.core.common.utils.NO_VALUE
import com.egorshustov.insearch.core.common.utils.UnixSeconds
import com.egorshustov.insearch.core.model.data.*

@Entity(tableName = "searches")
data class SearchEntity(

    @Embedded(prefix = "country_")
    val country: CountryEmbedded,

    @Embedded(prefix = "city_")
    val city: CityEmbedded,

    @ColumnInfo(name = "home_town")
    val homeTown: String?,

    val gender: Gender,

    @ColumnInfo(name = "age_from")
    val ageFrom: Int,

    @ColumnInfo(name = "age_to")
    val ageTo: Int?,

    val relation: Relation,

    @ColumnInfo(name = "with_phone_only")
    val withPhoneOnly: Boolean,

    @ColumnInfo(name = "found_users_limit")
    val foundUsersLimit: Int,

    @ColumnInfo(name = "days_interval")
    val daysInterval: Int,

    @ColumnInfo(name = "friends_min_count")
    val friendsMinCount: Int?,

    @ColumnInfo(name = "friends_max_count")
    val friendsMaxCount: Int?,

    @ColumnInfo(name = "followers_min_count")
    val followersMinCount: Int,

    @ColumnInfo(name = "followers_max_count")
    val followersMaxCount: Int,

    @ColumnInfo(name = "start_unix_seconds")
    val startUnixSeconds: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}

fun SearchEntity.asExternalModel(foundUsersCount: Int? = null) = Search(
    country = Country(country.id ?: NO_VALUE, country.title),
    city = City(city.id ?: NO_VALUE, city.title, "", ""),
    homeTown = homeTown,
    gender = gender,
    ageFrom = ageFrom,
    ageTo = ageTo,
    relation = relation,
    withPhoneOnly = withPhoneOnly,
    foundUsersLimit = foundUsersLimit,
    daysInterval = daysInterval,
    friendsMinCount = friendsMinCount,
    friendsMaxCount = friendsMaxCount,
    followersMinCount = followersMinCount,
    followersMaxCount = followersMaxCount,
    startTime = UnixSeconds(startUnixSeconds),
    id = id,
    foundUsersCount = foundUsersCount
)