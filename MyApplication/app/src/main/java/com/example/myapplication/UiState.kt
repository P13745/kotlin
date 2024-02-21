package com.example.myapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.Edge
import com.example.myapplication.data.EdgeViewModel
//import com.example.myapplication.data.EdgeViewModel
import com.example.myapplication.data.Vertex
import com.example.myapplication.data.VertexViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.lang.Math.random

const val YMax = 1000f
const val YMin = 0f + 50f
const val XMax = 1000f
const val XMin = 0f + 50f


data class UiState(
    var vertexList: List<Vertex>//List<Triple<Int, Float, Float>>
    = listOf(
        Vertex(0, 500f, 900f),
        Vertex(1 ,400f, 150f),
        Vertex(2, 600f, 400f),
        Vertex(3, 300f, 700f),
        Vertex(4, 900f, 500f)),
    // (internal id, x, y)
    var edgeList: List<Edge> //List<Triple<Int, Int, Int>>
    = listOf(
        Edge(0,0, 1),
        Edge(1,0, 2),
        Edge(2,2, 1),
        Edge(3,0, 3),
        Edge(4,1, 3),
        Edge(5,2, 4),
        Edge(6,4, 4),
        Edge(7,0, 4)),
    val startQuery: Int? = null,
    val endQuery: Int? = null,
    val movingSpeed: Float = 50f,
    val directed: Boolean = false
)

class GraphViewModel(
    //private val edgeViewModel: EdgeViewModel,
    //private val vertexViewModel: VertexViewModel
) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

/*
    //===============
    init {
        loadDataFromDatabase()
    }


    private fun loadDataFromDatabase() {
        viewModelScope.launch {
            val edgeList = edgeViewModel.getFullEdge().firstOrNull() ?: emptyList()
            val vertexList = vertexViewModel.getFullVertex().firstOrNull() ?: emptyList()

            _uiState.value = UiState(
                vertexList = vertexList,
                edgeList = edgeList
            )
        }
    }
    //===============

 */



    fun save(vertexList: List<Vertex>, edgeList: List<Edge>){
        var currentUiState = _uiState.value.copy()
        _uiState.value = currentUiState.copy(vertexList = vertexList, edgeList = edgeList)

    }


    fun moveVertex(id: Int, direction: String, distance: Float) {
        var currentUiState = _uiState.value.copy()
        if (currentUiState.vertexList.size > id && id >= 0) {
            var X = currentUiState.vertexList[id].x
            var Y = currentUiState.vertexList[id].y
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
            updatedVertexList[id] = updatedVertexList[id].copy(x = X, y = Y)

            _uiState.value = currentUiState.copy(vertexList = updatedVertexList)

        }
    }

    fun addNewVertex() {
        var currentUiState = _uiState.value.copy()
        var updatedVertexList = currentUiState.vertexList.toMutableList()

        val randomX = XMin + (XMax - XMin) * random().toFloat()
        val randomY = YMin + (YMax - YMin) * random().toFloat()

        updatedVertexList.add(Vertex(updatedVertexList.size, randomX, randomY))

        _uiState.value = currentUiState.copy(vertexList = updatedVertexList)

    }


    fun deleteVertex(id: Int) {
        var currentUiState = _uiState.value.copy()
        var updatedVertexList = currentUiState.vertexList.toMutableList()
        var updatedEdgeList = currentUiState.edgeList.toMutableList()

        if (currentUiState.vertexList.size > id && id >= 0) {
            updatedVertexList.removeAt(id)
            updatedEdgeList =
                updatedEdgeList.filter { edge -> edge.startId != id && edge.endId != id }
                    .toMutableList()
        }

        val reindexedEdgeList = updatedEdgeList.sortedBy { it.id }.mapIndexed { index, edge ->
            Edge(index, edge.startId, edge.endId)}
        val reindexedVertexList = updatedVertexList.sortedBy { it.id }.mapIndexed { index, vertex ->
            Vertex(index, vertex.x, vertex.y)}


        _uiState.value =
            currentUiState.copy(vertexList = reindexedVertexList, edgeList = reindexedEdgeList)


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
        var currentUiState = _uiState.value.copy()
        var updatedEdgeList = currentUiState.edgeList.toMutableList()

        updatedEdgeList =
            updatedEdgeList.filter { edge -> Pair(edge.startId, edge.endId) != selectedEdge && (Pair(edge.endId, edge.startId) != selectedEdge ||directed ) }
                .toMutableList()

        val reindexedEdgeList = updatedEdgeList.sortedBy { it.id }.mapIndexed { index, edge ->
            Edge(index, edge.startId, edge.endId)}

        _uiState.value = currentUiState.copy( edgeList = reindexedEdgeList)



    }

    //var nowStartQuery by mutableStateOf(0)
      //  private set
    fun updateStartQuery(query: Int?){
        //nowStartQuery = query ?: 0
        _uiState.value = _uiState.value.copy(startQuery = query)
    }




    //var nowEndQuery by mutableStateOf(0)
      //  private set
    fun updateEndQuery(query: Int?){
        //nowEndQuery = query ?: 0
        _uiState.value = _uiState.value.copy(endQuery = query)
    }


}
