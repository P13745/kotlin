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

    suspend fun deleteAllVertex(): Unit = vertexDao.deleteAll()

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

class VertexViewModel2(private val vertexDao2: VertexDao2): ViewModel() {

    fun getFullVertex2(): Flow<List<Vertex2>>  {
        return vertexDao2.getAll2().map { vertexList ->
            if (vertexList.isEmpty()) return@map emptyList()

            //==================================================================
            val mutableList = vertexList.toMutableList()
            val lastElement = mutableList.removeAt(mutableList.size - 1)
            mutableList.add(0, lastElement)
            //==================================================================

            mutableList.mapIndexed { index, vertex ->
                vertex.copy(id = index )
            }
        }
    }

    suspend fun deleteAllVertex2(): Unit = vertexDao2.deleteAll2()
    suspend fun insertAllVertex2(vertexList2: List<Vertex2>): Unit = vertexDao2.insertAll2(vertexList2)

    companion object {
        val factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as GraphApplication)
                VertexViewModel2(application.database.vertexDao2())
            }
        }
    }
}

class VertexViewModel3(private val vertexDao3: VertexDao3): ViewModel() {

    fun getFullVertex3(): Flow<List<Vertex3>>  {
        return vertexDao3.getAll3().map { vertexList ->
            if (vertexList.isEmpty()) return@map emptyList()

            //==================================================================
            val mutableList = vertexList.toMutableList()
            val lastElement = mutableList.removeAt(mutableList.size - 1)
            mutableList.add(0, lastElement)
            //==================================================================

            mutableList.mapIndexed { index, vertex ->
                vertex.copy(id = index )
            }
        }
    }

    suspend fun deleteAllVertex3(): Unit = vertexDao3.deleteAll3()
    suspend fun insertAllVertex3(vertexList3: List<Vertex3>): Unit = vertexDao3.insertAll3(vertexList3)

    companion object {
        val factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as GraphApplication)
                VertexViewModel3(application.database.vertexDao3())
            }
        }
    }
}