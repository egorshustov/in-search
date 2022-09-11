package com.egorshustov.vpoiske.core.database.util

import androidx.room.TypeConverter
import com.egorshustov.vpoiske.core.model.data.Gender
import com.egorshustov.vpoiske.core.model.data.Relation

/**
 * Enum type converters described there are not very necessary,
 * because starting from version 2.3.0 Room has a Built-in Enum Support
 * which converts Enum to String and vice versa.
 * But I chose to save an Id into the database instead of Enum name string,
 * because Id is more meaningful data to save in these cases.
 */

/**
 * Room type converter class for [Gender] enum model.
 */
class GenderConverter {

    @TypeConverter
    fun intToGender(genderId: Int?): Gender? = Gender.getByIdOrNull(genderId)

    @TypeConverter
    fun genderToInt(gender: Gender?): Int? = gender?.id
}

/**
 * Room type converter class for [Relation] enum model.
 */
class RelationConverter {

    @TypeConverter
    fun intToRelation(relationId: Int?): Relation? = Relation.getByIdOrNull(relationId)

    @TypeConverter
    fun relationToInt(relation: Relation?): Int? = relation?.id
}