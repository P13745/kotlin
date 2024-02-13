package com.example.myapplication

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun GraphApp(){
    val t = 50f
    val coordinates = listOf(
        //Pair(t*-1, t*-1),
        //Pair(t*0, t*0),
        //Pair(t*1, t*1),
        Pair(t*2, t*2),
        Pair(t*3, t*3),
        Pair(t*4, t*4),
        Pair(t*5, t*5),
        Pair(t*6, t*6),
        Pair(t*7, t*7),
        Pair(t*8, t*8),
        Pair(t*9, t*9),
        Pair(t*10, t*10),
        Pair(t*11, t*11),
        Pair(t*12, t*12),
        Pair(t*13, t*13),
        Pair(t*14, t*14),
        Pair(t*15, t*15),
        Pair(t*16, t*16),
        Pair(t*17, t*17),
        Pair(t*18, t*18),
        Pair(t*19, t*19),
        Pair(t*20, t*20),
        Pair(t*20+10, t*20+10),
        Pair(t*20+20, t*20+20),
        Pair(t*20+30, t*20+30),
        Pair(t*20+40, t*20+40),
        Pair(t*20+50, t*20+50),
        /*Pair(t*21, t*21),
        Pair(t*22, t*22),
        Pair(t*23, t*23),
        Pair(t*24, t*24),

         */



    )

    val graphViewModel: GraphViewModel = viewModel()
    val uiState = graphViewModel.uiState.collectAsState().value

    //val edgeList by graphViewModel.edgeList
    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
        ) {
            VertexFromList(
                uiState.vertexList,
                circleSize = 8.dp
            )/*
            EdgeFromList(
                edgeList = uiState.edgeList,
                vertexList = uiState.vertexList,
                directed =false
            )
            */
        }
        Box(modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
        ) {
            EditTable(
                uiState = uiState,
                addVertex = { graphViewModel.addNewVertex() },
                deleteVertex = { //indexを取得、見つからなければ-1を返す*/
                    val index = uiState.vertexList.indexOfFirst { triple ->
                        triple.second == it.first && triple.third == it.second }
                    graphViewModel.deleteVertex(index)
                               },
                addEdge = { (start, end) ->
                    graphViewModel.addNewEdge(
                        start,
                        end,
                        directed = false
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
                startQuery = graphViewModel.nowStartQuery,
                endQuery  = graphViewModel.nowEndQuery
            )
        }
        
    }

}

