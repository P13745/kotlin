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

    suspend fun deleteAllEdge(): Unit = edgeDao.deleteAll()

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

class EdgeViewModel2(private val edgeDao2: EdgeDao2): ViewModel() {


    fun getFullEdge2(): Flow<List<Edge2>>  {
        return edgeDao2.getAll2().map { edgeList ->
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

    suspend fun deleteAllEdge2(): Unit = edgeDao2.deleteAll2()

    suspend fun insertAllEdge2(edgeList2: List<Edge2>): Unit = edgeDao2.insertAll2(edgeList2)



    companion object {
        val factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as GraphApplication)
                EdgeViewModel2(application.database.edgeDao2())
            }
        }
    }
}

class EdgeViewModel3(private val edgeDao3: EdgeDao3): ViewModel() {


    fun getFullEdge3(): Flow<List<Edge3>>  {
        return edgeDao3.getAll3().map { edgeList ->
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

    suspend fun deletAllEdge3(): Unit = edgeDao3.deleteAll3()

    suspend fun insertAllEdge3(edgeList3: List<Edge3>): Unit = edgeDao3.insertAll3(edgeList3)



    companion object {
        val factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as GraphApplication)
                EdgeViewModel3(application.database.edgeDao3())
            }
        }
    }
}
