package com.example.myapplication.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface EdgeDao {
    @Query("SELECT * FROM edge")
    fun getAll(): Flow<List<Edge>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(edges: List<Edge>)

    @Query("DELETE FROM edge")
    suspend fun deleteAll()

}

@Dao
interface EdgeDao2 {
    @Query("SELECT * FROM edge2")
    fun getAll2(): Flow<List<Edge2>>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll2(edges: List<Edge2>)
    @Query("DELETE FROM edge2")
    suspend fun deleteAll2()

}
@Dao
interface EdgeDao3 {
    @Query("SELECT * FROM edge3")
    fun getAll3(): Flow<List<Edge3>>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll3(edges: List<Edge3>)
    @Query("DELETE FROM edge3")
    suspend fun deleteAll3()

}