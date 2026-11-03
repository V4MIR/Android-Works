package com.example.androidworks.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM favorites ORDER BY savedAt DESC")
    fun observeAll(): Flow<List<FavoriteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: FavoriteEntity)

    @Query("DELETE FROM favorites WHERE objectId = :objectId")
    suspend fun delete(objectId: Int)

    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE objectId = :objectId)")
    suspend fun exists(objectId: Int): Boolean
}