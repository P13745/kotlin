package com.example.myapplication

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.lang.Math.random

const val YMax = 1000f
const val YMin = 0f + 50f
const val XMax = 1000f
const val XMin = 0f + 50f


data class UiState(
    var vertexList: List<Triple<Int, Float, Float>> = listOf( Triple(0, 50f, 50f), Triple(1 ,150f, 150f), Triple(2, 150f, 400f), Triple(3, 400f, 400f) ),
    // (internal id, x, y)
    var edgeList: List<Triple<Int, Int, Int>> = /*lisOf()*/ listOf( Triple(0,0, 1), Triple(1,0, 2), Triple(2,2, 1), Triple(3,0, 3)),
    // (internal id, start id(vertex), end id(vertex))
    val startQuery: Int = -1,
    val endQuery: Int = -1,
    val selectedEdge: Pair<Int, Int > = (-1 to -1),
    val movingSpeed: Float = 50f
)

class GraphViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

/*
    var vertexList: List<Pair<Float, Float>> by mutableStateOf(listOf())
        private set
    var edgeList: List<Pair<Int, Int>> by mutableStateOf(listOf())
        private set

    fun reset() {
        _uiState.value = UiState()
        vertexList = listOf()
        edgeList = listOf()
    }

 */

    fun moveVertex(id: Int, direction: String, distance: Float) {
        var currentUiState = _uiState.value.copy()
        if (currentUiState.vertexList.size > id && id >= 0) {
            var X = currentUiState.vertexList[id].second
            var Y = currentUiState.vertexList[id].third
            when (direction) {
                "UP" -> Y -= distance
                "DOWN" -> Y += distance
                "RIGHT" -> X += distance
                "LEFT" -> X -= distance
                else -> {}
            }
            X = X.coerceIn(XMin, XMax) // XMinとXMaxの間に収める
            Y = Y.coerceIn(YMin, YMax) // YMinとYMaxの間に収める

            var updatedVertexList = currentUiState.vertexList.toMutableList()
            updatedVertexList[id] = updatedVertexList[id].copy(second = X, third = Y)

            _uiState.value = currentUiState.copy(vertexList = updatedVertexList)

        }
    }

    fun addNewVertex() {
        var currentUiState = _uiState.value.copy()
        var updatedVertexList = currentUiState.vertexList.toMutableList()

        val randomX = XMin + (XMax - XMin) * random().toFloat()
        val randomY = YMin + (YMax - YMin) * random().toFloat()

        updatedVertexList.add(Triple(updatedVertexList.size, randomX, randomY))

        _uiState.value = currentUiState.copy(vertexList = updatedVertexList)

    }


    fun deleteVertex(id: Int) {
        var currentUiState = _uiState.value.copy()
        var updatedVertexList = currentUiState.vertexList.toMutableList()
        var updatedEdgeList = currentUiState.edgeList.toMutableList()

        if (currentUiState.vertexList.size > id && id >= 0) {
            updatedVertexList.removeAt(id)
            updatedEdgeList =
                updatedEdgeList.filter { pair -> pair.second != id && pair.third != id }
                    .toMutableList()
        }
        _uiState.value =
            currentUiState.copy(vertexList = updatedVertexList, edgeList = updatedEdgeList)


    }

    fun addNewEdge(start: Int, end: Int, directed: Boolean){
        var currentUiState = _uiState.value.copy()
        var updatedEdgeList = currentUiState.edgeList.toMutableList()
        var updatedVertexList = currentUiState.vertexList.toMutableList()

        if (start >= 0
            && end >= 0
            && start != end
            && start < updatedVertexList.size
            && end < updatedVertexList.size){
            if(
                !updatedEdgeList.any { it.second == start && it.third == end }
                && (!updatedEdgeList.any { it.second == end && it.third == start }|| directed)
                ) {
                updatedEdgeList.add(Triple(updatedEdgeList.size, start, end))
            }
        }
        _uiState.value =
            currentUiState.copy(vertexList = updatedVertexList, edgeList = updatedEdgeList)
    }

    fun deleteEdge(edge: Pair<Int,Int>, directed: Boolean){
        var currentUiState = _uiState.value.copy()
        var updatedEdgeList = currentUiState.edgeList.toMutableList()

        updatedEdgeList =
            updatedEdgeList.filter { triple -> Pair(triple.second, triple.third) != edge && (Pair(triple.third, triple.second) != edge ||directed ) }
                .toMutableList()

        val reindexedList = updatedEdgeList.sortedBy { it.first }.mapIndexed { index, triple ->
            Triple(index, triple.second, triple.third)}

        _uiState.value = currentUiState.copy( edgeList = reindexedList,)



    }

    var nowStartQuery by mutableStateOf(0)
        private set
    fun updateStartQuery(query: Int?){
        nowStartQuery = query ?: -1
    }




    var nowEndQuery by mutableStateOf(0)
        private set
    fun updateEndQuery(query: Int?){
        nowEndQuery = query ?: -1
    }
/*
    fun setEdge(edge: Pair<Int, Int>?) {
        val currentUiState = _uiState.value
        if (edge != null) {
            _uiState.value = currentUiState.copy(selectedEdge = edge)
        } else {
            _uiState.value = currentUiState.copy(selectedEdge = (-1 to -1))
        }
    }

 */

}
