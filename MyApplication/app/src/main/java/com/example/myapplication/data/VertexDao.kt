package com.example.myapplication.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface  VertexDao {
    @Query("SELECT * FROM vertex")
    fun getAll(): Flow<List<Vertex>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(vertex: List<Vertex>)

    @Query("DELETE FROM vertex")
    suspend fun deleteAll()
}
@Dao
interface VertexDao2 {
    @Query("SELECT * FROM vertex2")
    fun getAll2(): Flow<List<Vertex2>>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll2(vertex: List<Vertex2>)
    @Query("DELETE FROM vertex2")
    suspend fun deleteAll2()
}
@Dao
interface VertexDao3 {
    @Query("SELECT * FROM vertex3")
    fun getAll3(): Flow<List<Vertex3>>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll3(vertex: List<Vertex3>)
    @Query("DELETE FROM vertex3")
    suspend fun deleteAll3()
}