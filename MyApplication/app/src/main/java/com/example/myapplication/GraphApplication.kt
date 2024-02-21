package com.example.myapplication

import android.app.Application
import com.example.myapplication.data.GraphDatabase

class GraphApplication: Application()/*, ViewModelStoreOwner */{
    val database: GraphDatabase by lazy {
        GraphDatabase.getDatabase(this)
    }


    //val graphViewModel: GraphViewModel by lazy { ViewModelProvider(this).get(GraphViewModel::class.java) }


/*
    override fun onCreate() {
        super.onCreate()
        // データベースからのデータ読み取りを開始
        GraphDatabase.getDatabase(this)
    }

 */
}