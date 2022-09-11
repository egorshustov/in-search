package com.egorshustov.vpoiske.core.database.dao

/**
 * Performs an upsert by first attempting to insert [item] using [insertOne] with the the result
 * of the insert returned.
 *
 * If item was not inserted due to conflict so then it is updated using [updateOne]
 */
internal suspend fun <T> upsert(
    item: T,
    insertOne: suspend (T) -> Long,
    updateOne: suspend (T) -> Unit,
) {
    val insertResult = insertOne(item)
    if (insertResult == -1L) updateOne(item)
}

/**
 * Performs an upsert by first attempting to insert [items] using [insertMany] with the the result
 * of the inserts returned.
 *
 * Items that were not inserted due to conflicts are then updated using [updateMany]
 */
internal suspend fun <T> upsertMany(
    items: List<T>,
    insertMany: suspend (List<T>) -> List<Long>,
    updateMany: suspend (List<T>) -> Unit,
) {
    val insertResults = insertMany(items)

    val updateList = items.zip(insertResults)
        .mapNotNull { (item, insertResult) ->
            if (insertResult == -1L) item else null
        }
    if (updateList.isNotEmpty()) updateMany(updateList)
}
