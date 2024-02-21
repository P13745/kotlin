package com.example.myapplication

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.data.GraphDatabase
import com.example.myapplication.data.EdgeViewModel
import com.example.myapplication.data.VertexViewModel


@Composable
fun GraphApp(
    vertexViewModel: VertexViewModel = viewModel(factory = VertexViewModel.factory),
    edgeViewModel: EdgeViewModel = viewModel(factory = EdgeViewModel.factory),
){
    val graphViewModel: GraphViewModel = viewModel() //右のではダメ!!  //GraphViewModel(/*edgeViewModel, vertexViewModel*/)
    val uiState = graphViewModel.uiState.collectAsState().value
    val vertexList by vertexViewModel.getFullVertex().collectAsState(emptyList())
    val edgeList by edgeViewModel.getFullEdge().collectAsState(emptyList())



    //val edgeList by graphViewModel.edgeList
    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
        ) {
            VertexAndEdgeFromList(
                vertexList =  uiState.vertexList ,
                edgeList = uiState.edgeList,
                directed = uiState.directed
            )

        }
        Box(modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
        ) {
            EditTable(
                uiState = uiState,
                addVertex = { graphViewModel.addNewVertex() },
                deleteVertex = { //indexを取得、見つからなければ-1を返す*/
                    val index = uiState.vertexList.indexOfFirst { vertex ->
                        vertex.x == it.first && vertex.y == it.second }
                    graphViewModel.deleteVertex(index)
                               },
                addEdge = { (start, end) ->
                    graphViewModel.addNewEdge(
                        start,
                        end,
                        directed = uiState.directed
                    )
                },
                deleteEdge = { graphViewModel.deleteEdge(it, directed = false) },
                onValueChangeOfStart = { graphViewModel.updateStartQuery(it) },
                onValueChangeOfEnd = { graphViewModel.updateEndQuery(it) },
                move = {
                        id, direction, distance
                    ->
                    graphViewModel.moveVertex(id, direction, distance)
                },
                movingSpeed = uiState.movingSpeed,
                startQuery = uiState.startQuery,
                endQuery  = uiState.endQuery,
                directed = uiState.directed
            )
        }
        
    }

}

