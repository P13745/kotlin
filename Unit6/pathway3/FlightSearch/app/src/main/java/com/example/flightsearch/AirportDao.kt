package com.example.flightsearch

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.jetbrains.annotations.NotNull


@Dao
interface  AirportDao {



    @Query("SELECT * FROM airport")
    fun getAll(): Flow<List<Airport>>


    @Query("SELECT * FROM airport WHERE (:query != '' AND (iata_code LIKE :query||'%' OR name LIKE :query||'%' ))ORDER BY passengers ASC")
    fun getFromQuery(query : String): Flow<List<Airport>>


}