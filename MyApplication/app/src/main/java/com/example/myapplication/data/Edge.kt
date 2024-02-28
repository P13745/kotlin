package com.example.myapplication.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "edge")
data class Edge(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "start_id") val startId: Int,
    @ColumnInfo(name = "end_id") val endId: Int
)

@Entity(tableName = "edge2")
data class Edge2(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "start_id") val startId: Int,
    @ColumnInfo(name = "end_id") val endId: Int
)

@Entity(tableName = "edge3")
data class Edge3(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "start_id") val startId: Int,
    @ColumnInfo(name = "end_id") val endId: Int
)

