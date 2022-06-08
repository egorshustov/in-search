package com.egorshustov.vpoiske.core.model.data

import android.content.Context
import androidx.annotation.StringRes
import com.egorshustov.vpoiske.core.model.R

enum class Gender(val id: Int, @StringRes private val descriptionRes: Int) {

    NOT_DEFINED(0, R.string.gender_any),
    FEMALE(1, R.string.gender_female),
    MALE(2, R.string.gender_male);

    fun getDescription(context: Context) = context.getString(descriptionRes)

    companion object {

        fun getByIdOrNull(genderId: Int?): Gender? = values().find { it.id == genderId }
    }
}