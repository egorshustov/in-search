package com.egorshustov.vpoiske.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.egorshustov.vpoiske.core.database.model.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface UserDao {

    @Query("SELECT * FROM users WHERE search_id = :searchId ORDER BY found_unix_millis")
    fun getUsersStream(searchId: Long): Flow<List<UserEntity>>

    @Query("SELECT COUNT(*) FROM users WHERE search_id = :searchId")
    fun getUsersCountStream(searchId: Long): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnoreUser(entity: UserEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnoreUsers(entities: List<UserEntity>): List<Long>
}