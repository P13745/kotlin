package com.example.myapplication

import android.app.Application
import com.example.myapplication.data.GraphDatabase

class GraphApplication: Application() {
    val database: GraphDatabase by lazy {
        GraphDatabase.getDatabase(this)
    }
}