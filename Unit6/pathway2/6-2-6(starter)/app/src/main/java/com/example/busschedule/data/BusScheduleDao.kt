package com.example.busschedule.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface BusScheduleDao {

        @Query("SELECT * FROM Schedule WHERE stop_Name =:name ORDER BY arrival_time ASC")
        fun getByName(name : String): Flow<List<BusSchedule>>

        @Query("SELECT * FROM Schedule ORDER BY arrival_time ASC")
        fun getAll(): Flow<List<BusSchedule>>

}