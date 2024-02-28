package com.example.myapplication.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "vertex")
data class Vertex(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "x") val x: Float,
    @ColumnInfo(name = "y") val y: Float,
)
@Entity(tableName = "vertex2")
data class Vertex2(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "x") val x: Float,
    @ColumnInfo(name = "y") val y: Float,
)
@Entity(tableName = "vertex3")
data class Vertex3(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "x") val x: Float,
    @ColumnInfo(name = "y") val y: Float,
)