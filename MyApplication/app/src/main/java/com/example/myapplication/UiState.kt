package com.example.myapplication

import androidx.lifecycle.ViewModel
import com.example.myapplication.data.Edge
import com.example.myapplication.data.Vertex
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.lang.Math.random

const val YMax = 1000f - 50f
const val YMin = 0f
const val XMax = 1000f
const val XMin = 0f + 50f


data class UiState(
    var vertexList: List<Vertex>
    = listOf(
        Vertex(0, 500f, 150f),
        Vertex(1, 250f, 350f),
        Vertex(2, 350f, 650f),
        Vertex(3, 650f, 650f),
        Vertex(4, 800f, 350f),
        Vertex(5, 500f,   0f),
        Vertex(6, 100f, 350f),
        Vertex(7, 250f, 800f),
        Vertex(8, 750f, 800f),
        Vertex(9, 950f, 350f),
    ),
    var edgeList: List<Edge>
    = listOf(
        Edge(0, 0, 2),
        Edge(1, 2, 4),
        Edge(2, 4, 1),
        Edge(3, 1, 3),
        Edge(4, 3, 0),
        Edge(5, 0, 5),
        Edge(6, 1, 6),
        Edge(7, 2, 7),
        Edge(8, 3, 8),
        Edge(9, 4, 9),
        Edge(10, 5, 6),
        Edge(11, 6, 7),
        Edge(12, 7, 8),
        Edge(13, 8, 9),
        Edge(14, 9, 5),
    ),
    val startQuery: Int? = null,
    val endQuery: Int? = null,
    val movingSpeed: Float = 50f,
    val directed: Boolean = false,
    val mode: String = "Vertex"
)

class GraphViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun load(vertexList: List<Vertex>, edgeList: List<Edge>){
        _uiState.value = _uiState.value.copy(vertexList = vertexList, edgeList = edgeList)
    }

    fun moveVertex(id: Int, direction: String, distance: Float) {
        val currentUiState = _uiState.value.copy()
        if (currentUiState.vertexList.size > id && id >= 0) {
            var x = currentUiState.vertexList[id].x
            var y = currentUiState.vertexList[id].y
            when (direction) {
                "UP" -> y -= distance
                "DOWN" -> y += distance
                "RIGHT" -> x += distance
                "LEFT" -> x -= distance
                else -> {}
            }
            x = x.coerceIn(XMin, XMax)
            y = y.coerceIn(YMin, YMax)

            val updatedVertexList = currentUiState.vertexList.toMutableList()
            updatedVertexList[id] = updatedVertexList[id].copy(x = x, y = y)

            _uiState.value = currentUiState.copy(vertexList = updatedVertexList)

        }
    }

    fun addNewVertex() {
        val currentUiState = _uiState.value.copy()
        val updatedVertexList = currentUiState.vertexList.toMutableList()

        val randomX = XMin + (XMax - XMin) * random().toFloat()
        val randomY = YMin + (YMax - YMin) * random().toFloat()

        updatedVertexList.add(Vertex(updatedVertexList.size, randomX, randomY))

        _uiState.value = currentUiState.copy(vertexList = updatedVertexList)

    }


fun deleteVertex(id: Int) {
    val currentUiState = _uiState.value.copy()
    val updatedVertexList = currentUiState.vertexList.toMutableList()
    var filteredEdgeList = currentUiState.edgeList.toMutableList()

    if (id >= 0 && id < currentUiState.vertexList.size) {

        updatedVertexList.removeAt(id)

        filteredEdgeList = filteredEdgeList.filter { edge ->
                       edge.startId != id
                    && edge.startId < currentUiState.vertexList.size
                    && edge.endId != id
                    && edge.endId <currentUiState.vertexList.size
        }.map {edge ->
            when {
                edge.startId >= id && edge.endId >= id -> {
                    edge.copy(startId = edge.startId - 1, endId = edge.endId - 1)
                }
                edge.startId >= id -> {
                    edge.copy(startId = edge.startId - 1)
                }
                edge.endId >= id -> {
                    edge.copy(endId = edge.endId - 1)
                }
                else -> {
                    edge // 条件を満たさない場合はそのままのエッジを返す
                }
            }
        }.toMutableList()
    }

    val reindexedEdgeList = filteredEdgeList.mapIndexed { index, edge ->
        Edge(index, edge.startId, edge.endId)
    }
    val reindexedVertexList = updatedVertexList.mapIndexed { index, vertex ->
        Vertex(index, vertex.x, vertex.y)
    }

    _uiState.value = currentUiState.copy(vertexList = reindexedVertexList, edgeList = reindexedEdgeList)
}

    fun addNewEdge(start: Int, end: Int, directed: Boolean){
        val currentUiState = _uiState.value.copy()
        val updatedEdgeList = currentUiState.edgeList.toMutableList()
        val updatedVertexList = currentUiState.vertexList.toMutableList()

        if (start >= 0
            && end >= 0
            && start != end
            && start < updatedVertexList.size
            && end < updatedVertexList.size){
            if(
                !updatedEdgeList.any { it.startId == start && it.endId == end }
                && (!updatedEdgeList.any { it.startId == end && it.endId == start }|| directed)
                ) {
                updatedEdgeList.add(Edge(updatedEdgeList.size, start, end))
            }
        }
        _uiState.value =
            currentUiState.copy(vertexList = updatedVertexList, edgeList = updatedEdgeList)
    }

    fun deleteEdge(selectedEdge: Pair<Int,Int>, directed: Boolean){
        val currentUiState = _uiState.value.copy()
        var updatedEdgeList = currentUiState.edgeList.toMutableList()

        updatedEdgeList =
            updatedEdgeList.filter { edge -> Pair(edge.startId, edge.endId) != selectedEdge && (Pair(edge.endId, edge.startId) != selectedEdge ||directed ) }
                .toMutableList()

        val reindexedEdgeList = updatedEdgeList.sortedBy { it.id }.mapIndexed { index, edge ->
            Edge(index, edge.startId, edge.endId)}

        _uiState.value = currentUiState.copy( edgeList = reindexedEdgeList)

    }

    fun updateMode(mode: String){
        _uiState.value = _uiState.value.copy(mode = mode)
    }

    fun updateStartQuery(query: Int?){

        _uiState.value = _uiState.value.copy(startQuery = query)
    }

    fun updateMovingSpeed(speed: Float){
        _uiState.value = _uiState.value.copy(movingSpeed = speed)
    }

    fun updateEndQuery(query: Int?){

        _uiState.value = _uiState.value.copy(endQuery = query)
    }


}
