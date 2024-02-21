package com.example.myapplication.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "edge")
data class Edge(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "start_id") val startId: Int,
    @ColumnInfo(name = "end_id:") val endId: Int
)