package com.example.myapplication.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.myapplication.GraphApplication
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class VertexViewModel(private val vertexDao: VertexDao): ViewModel() {


    //fun getFullVertex(): Flow<List<Vertex>> = vertexDao.getAll()

    fun getFullVertex(): Flow<List<Vertex>>  {
        return vertexDao.getAll().map { vertexList ->
            if (vertexList.isEmpty()) return@map emptyList()

            //==================================================================
            val mutableList = vertexList.toMutableList()
            val lastElement = mutableList.removeAt(mutableList.size - 1)
            mutableList.add(0, lastElement)
            //==================================================================

            mutableList.mapIndexed { index, vertex ->
                vertex.copy(id = index )        ///??????????????????
            }
        }
    }

    suspend fun deletAllVertex(): Unit = vertexDao.deleteAll()

    suspend fun insertAllVertex(vertexList: List<Vertex>): Unit = vertexDao.insertAll(vertexList)






    companion object {
        val factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as GraphApplication)
                VertexViewModel(application.database.vertexDao())
            }
        }
    }
}