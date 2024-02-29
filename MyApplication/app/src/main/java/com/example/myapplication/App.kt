package com.example.myapplication

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.example.myapplication.data.Edge2
import com.example.myapplication.data.Edge3
import com.example.myapplication.data.EdgeViewModel
import com.example.myapplication.data.EdgeViewModel2
import com.example.myapplication.data.EdgeViewModel3
import com.example.myapplication.data.Vertex
import com.example.myapplication.data.Vertex2
import com.example.myapplication.data.Vertex3
import com.example.myapplication.data.VertexViewModel
import com.example.myapplication.data.VertexViewModel2
import com.example.myapplication.data.VertexViewModel3
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun GraphApp(
    vertexViewModel: VertexViewModel = viewModel(factory = VertexViewModel.factory),
    edgeViewModel: EdgeViewModel = viewModel(factory = EdgeViewModel.factory),
    vertexViewModel2: VertexViewModel2 = viewModel(factory = VertexViewModel2.factory),
    edgeViewModel2: EdgeViewModel2 = viewModel(factory = EdgeViewModel2.factory),
    vertexViewModel3: VertexViewModel3 = viewModel(factory = VertexViewModel3.factory),
    edgeViewModel3: EdgeViewModel3 = viewModel(factory = EdgeViewModel3.factory),
) {
    val graphViewModel: GraphViewModel =
        viewModel() //右のではダメ!!  //GraphViewModel(/*edgeViewModel, vertexViewModel*/)
    val uiState = graphViewModel.uiState.collectAsState().value
    val vertexList by vertexViewModel.getFullVertex().collectAsState(emptyList())
    val edgeList by edgeViewModel.getFullEdge().collectAsState(emptyList())
    val vertexList2 by vertexViewModel2.getFullVertex2().collectAsState(emptyList())
    val edgeList2 by edgeViewModel2.getFullEdge2().collectAsState(emptyList())
    val vertexList3 by vertexViewModel3.getFullVertex3().collectAsState(emptyList())
    val edgeList3 by edgeViewModel3.getFullEdge3().collectAsState(emptyList())



    var loadingOrSaving by remember { mutableStateOf(false) }

    //val edgeList by graphViewModel.edgeList
    Column(modifier = Modifier.fillMaxSize()) {
        GraphTopBar()

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            VertexAndEdgeFromList(
                vertexList = uiState.vertexList,
                edgeList = uiState.edgeList,
                directed = uiState.directed
            )
        }

        Box(
            modifier = Modifier
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
                move = { id, direction, distance ->
                    graphViewModel.moveVertex(id, direction, distance)
                },
                movingSpeed = uiState.movingSpeed,
                startQuery = uiState.startQuery,
                endQuery = uiState.endQuery,
                directed = uiState.directed,
                loadingOrSaving = loadingOrSaving,
                modeChange = { graphViewModel.updateMode(it) },
                saveAction = {
                    CoroutineScope(Dispatchers.Main).launch {
                        loadingOrSaving = true
                        edgeViewModel.deleteAllEdge()
                        vertexViewModel.deleteAllVertex()
                        vertexViewModel.insertAllVertex(uiState.vertexList)
                        edgeViewModel.insertAllEdge(uiState.edgeList)
                        loadingOrSaving = false
                    }
                },
                loadAction = { graphViewModel.load(vertexList, edgeList) },
                saveAction2 = {
                    CoroutineScope(Dispatchers.Main).launch {
                        loadingOrSaving = true
                        edgeViewModel2.deleteAllEdge2()
                        vertexViewModel2.deleteAllVertex2()
                        vertexViewModel2.insertAllVertex2(
                            uiState.vertexList.map { vertex ->
                                Vertex2(
                                    id = vertex.id,
                                    x = vertex.x,
                                    y = vertex.y
                                )
                            }
                        )
                        edgeViewModel2.insertAllEdge2(
                            uiState.edgeList.map { edge ->
                                Edge2(
                                    id = edge.id,
                                    startId = edge.startId,
                                    endId = edge.endId
                                )
                            }
                        )
                        loadingOrSaving = false
                    }
                },
                loadAction2 = { graphViewModel.load(
                    vertexList2.map { vertex2 ->
                        Vertex(
                            id = vertex2.id,
                            x = vertex2.x,
                            y = vertex2.y
                        )
                                    },
                    edgeList2.map { edge2 ->
                        Edge(
                            id = edge2.id,
                            startId = edge2.startId,
                            endId = edge2.endId
                        )
                    }
                ) },
                saveAction3 = {
                    CoroutineScope(Dispatchers.Main).launch {
                        loadingOrSaving = true
                        edgeViewModel3.deletAllEdge3()
                        vertexViewModel3.deleteAllVertex3()
                        vertexViewModel3.insertAllVertex3(
                            uiState.vertexList.map { vertex ->
                                Vertex3(
                                    id = vertex.id,
                                    x = vertex.x,
                                    y = vertex.y
                                )
                            }
                        )
                        edgeViewModel3.insertAllEdge3(
                            uiState.edgeList.map { edge ->
                                Edge3(
                                    id = edge.id,
                                    startId = edge.startId,
                                    endId = edge.endId
                                )
                            }
                        )
                        loadingOrSaving = false
                    }
                },

                loadAction3 = { graphViewModel.load(
                    vertexList3.map { vertex3 ->
                        Vertex(
                            id = vertex3.id,
                            x = vertex3.x,
                            y = vertex3.y
                        )
                    },
                    edgeList3.map { edge3 ->
                        Edge(
                            id = edge3.id,
                            startId = edge3.startId,
                            endId = edge3.endId
                        )
                    }
                ) },
                emptyOrNot = vertexList.isEmpty(),
                emptyOrNot2 = vertexList2.isEmpty(),
                emptyOrNot3 = vertexList3.isEmpty(),

                )
        }

    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GraphTopBar() {
    Box() {
        CenterAlignedTopAppBar(
            title = { Text("Graph application") }
        )
    }

}


