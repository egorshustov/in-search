package com.egorshustov.vpoiske.core.model.data

import android.content.Context
import androidx.annotation.StringRes
import com.egorshustov.vpoiske.core.model.R

enum class Relation(
    val id: Int,
    @StringRes private val descriptionFemaleRes: Int,
    @StringRes private val descriptionMaleRes: Int,
    val gender: Gender = Gender.FEMALE
) {

    NOT_DEFINED(0, R.string.relation_female_any, R.string.relation_male_any),
    NOT_MARRIED(1, R.string.relation_female_not_married, R.string.relation_male_not_married),
    HAS_FRIEND(2, R.string.relation_female_has_friend, R.string.relation_male_has_friend),
    ENGAGED(3, R.string.relation_female_engaged, R.string.relation_male_engaged),
    MARRIED(4, R.string.relation_female_married, R.string.relation_male_married),
    ALL_COMPLICATED(5, R.string.relation_female_all_complicated, R.string.relation_male_all_complicated),
    IN_ACTIVE_SEARCH(6, R.string.relation_female_in_active_search, R.string.relation_male_in_active_search),
    IN_LOVE(7, R.string.relation_female_in_love, R.string.relation_male_in_love),
    IN_CIVIL_MARRIAGE(8, R.string.relation_female_in_civil_marriage, R.string.relation_male_in_civil_marriage);

    fun getDescription(context: Context) =
        context.getString(if (gender == Gender.FEMALE) descriptionFemaleRes else descriptionMaleRes)

    companion object {

        fun getByIdOrNull(relationId: Int?): Relation? = values().find { it.id == relationId }
    }
}