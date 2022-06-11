package com.egorshustov.vpoiske.core.database.model

import androidx.room.Embedded
import androidx.room.Relation

data class SearchWithUsersPopulated(
    @Embedded val search: SearchEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "search_id"
    )
    val users: List<UserEntity>
)