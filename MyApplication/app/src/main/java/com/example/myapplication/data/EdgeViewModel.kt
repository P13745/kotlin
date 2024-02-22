package com.example.myapplication.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.myapplication.GraphApplication
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class EdgeViewModel(private val edgeDao: EdgeDao): ViewModel() {


    fun getFullEdge(): Flow<List<Edge>>  {
        return edgeDao.getAll().map { edgeList ->
            if (edgeList.isEmpty()) return@map emptyList()


            //==================================================================
            val mutableList = edgeList.toMutableList()
            val lastElement = mutableList.removeAt(mutableList.size - 1)
            mutableList.add(0, lastElement)
            //==================================================================

            mutableList.mapIndexed { index, edge ->
                edge.copy(id = index)
            }
        }
    }

    suspend fun deletAllEdge(): Unit = edgeDao.deleteAll()

    suspend fun insertAllEdge(edgeList: List<Edge>): Unit = edgeDao.insertAll(edgeList)



    companion object {
        val factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as GraphApplication)
                EdgeViewModel(application.database.edgeDao())
            }
        }
    }
}

