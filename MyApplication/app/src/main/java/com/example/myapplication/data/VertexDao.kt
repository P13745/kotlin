package com.example.myapplication.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow



@Dao
interface  VertexDao {
    @Query("SELECT * FROM vertex")
    fun getAll(): Flow<List<Vertex>>

    @Delete
    suspend fun delete(vertex: Vertex)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(vertex: Vertex)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(vertex: List<Vertex>)


/*
    @Query("CREATE TABLE IF NOT EXISTS vertex (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "x REAL NOT NULL, " +
            "y REAL NOT NULL)")
    suspend fun createTable()

 */


}