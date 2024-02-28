package com.example.myapplication.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Vertex::class, Edge::class, Vertex2::class, Edge2::class, Vertex3::class, Edge3::class], version = 1)
abstract class GraphDatabase: RoomDatabase() {

    abstract fun vertexDao(): VertexDao
    abstract fun edgeDao(): EdgeDao

    abstract fun vertexDao2(): VertexDao2
    abstract fun edgeDao2(): EdgeDao2
    abstract fun vertexDao3(): VertexDao3
    abstract fun edgeDao3(): EdgeDao3



    companion object {
        @Volatile
        private var INSTANCE: GraphDatabase? = null

        fun getDatabase(context: Context): GraphDatabase {
            //val x = context.applicationContext.filesDir
            return INSTANCE ?: synchronized(this) {
                //Log.v("### External ###", "${context.applicationContext}")
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GraphDatabase::class.java,
                    "graph.db"
                )
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }




}