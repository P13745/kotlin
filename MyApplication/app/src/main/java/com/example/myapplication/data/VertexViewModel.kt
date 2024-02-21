package com.example.myapplication.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.myapplication.GraphApplication
import kotlinx.coroutines.flow.Flow

class VertexViewModel(private val vertexDao: VertexDao): ViewModel() {


    fun getFullVertex(): Flow<List<Vertex>> = vertexDao.getAll()


    companion object {
        val factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as GraphApplication)
                VertexViewModel(application.database.vertexDao())
            }
        }
    }
}