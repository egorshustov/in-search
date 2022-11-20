package com.egorshustov.insearch.core.database.model

import androidx.room.Embedded
import androidx.room.Relation
import com.egorshustov.insearch.core.model.data.SearchWithUsers

data class SearchWithUsersPopulated(
    @Embedded val search: SearchEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "search_id"
    )
    val users: List<UserEntity>
)

fun SearchWithUsersPopulated.asExternalModel() = SearchWithUsers(
    search = search.asExternalModel(foundUsersCount = users.size),
    users = users.asExternalModelList()
)