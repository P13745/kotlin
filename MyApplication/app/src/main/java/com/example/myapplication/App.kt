package com.example.myapplication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.data.Edge
import com.example.myapplication.data.GraphDatabase
import com.example.myapplication.data.EdgeViewModel
import com.example.myapplication.data.Vertex
import com.example.myapplication.data.VertexViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun GraphApp(
    vertexViewModel: VertexViewModel = viewModel(factory = VertexViewModel.factory),
    edgeViewModel: EdgeViewModel = viewModel(factory = EdgeViewModel.factory),
){
    val graphViewModel: GraphViewModel = viewModel() //右のではダメ!!  //GraphViewModel(/*edgeViewModel, vertexViewModel*/)
    val uiState = graphViewModel.uiState.collectAsState().value
    val vertexList by vertexViewModel.getFullVertex().collectAsState(emptyList())
    val edgeList by edgeViewModel.getFullEdge().collectAsState(emptyList())




    var loadingOrSaving by remember { mutableStateOf(false) }



    //val edgeList by graphViewModel.edgeList
    Column(modifier = Modifier.fillMaxSize()) {
        GraphTopBar(
            loadAction =  {
                loadingOrSaving = true
                graphViewModel.load(vertexList, edgeList)
                loadingOrSaving = false
                          },
            saveAction = {
                CoroutineScope(Dispatchers.Main).launch {
                    loadingOrSaving = true
                    edgeViewModel.deletAllEdge()
                    vertexViewModel.deletAllVertex()
                    vertexViewModel.insertAllVertex(uiState.vertexList)
                    edgeViewModel.insertAllEdge(uiState.edgeList)
                    loadingOrSaving = false
                }

            }

        )

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
                deleteVertex = { graphViewModel.deleteVertex(it) },
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
                directed = uiState.directed,
                loadingOrSaving = loadingOrSaving
            )
        }
        
    }

}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GraphTopBar(
    loadAction: () -> Unit,
    saveAction: () -> Unit,
){
    Box() {
        CenterAlignedTopAppBar(
            title = { Text("Graph application") }
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
        ){
            Button(
                content = { Text("Load") },
                onClick = loadAction
            )
            Spacer(modifier = Modifier.weight(1f))
            Button(
                content = {Text("Save")},
                onClick = saveAction
            )

        }
    }

}

