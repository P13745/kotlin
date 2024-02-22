package com.example.myapplication.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow



@Dao
interface  VertexDao {
    @Query("SELECT * FROM vertex ")
    fun getAll(): Flow<List<Vertex>>

    @Delete
    suspend fun delete(vertex: Vertex)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(vertex: Vertex)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(vertex: List<Vertex>)

    @Query("DELETE FROM vertex") // すべてのデータを削除するクエリ
    suspend fun deleteAll()





}