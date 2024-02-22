package com.example.myapplication.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface EdgeDao {
    @Query("SELECT * FROM edge")
    fun getAll(): Flow<List<Edge>>

    @Delete
    suspend fun delete(edge: Edge)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(edge: Edge)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(edges: List<Edge>)

    @Query("DELETE FROM edge")
    suspend fun deleteAll()

}