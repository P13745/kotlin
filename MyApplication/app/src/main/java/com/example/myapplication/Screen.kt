package com.example.myapplication

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel



@Composable
fun VertexFromList(vertexList: List<Triple<Int, Float, Float>>, circleSize: Dp) {
    val shift = 60f
    // サイズの変換
    val circleSizePx = with(LocalDensity.current) { circleSize.toPx() }

    Canvas(
        modifier = Modifier.fillMaxSize()
    ) {
        vertexList.forEach {
            drawCircle(
                color = Color.Blue,
                radius = circleSizePx / 2f,
                center = Offset(it.second, it.third)
            )
            val text = (vertexList.indexOf(it) + 1).toString()
            val paint = android.graphics.Paint().apply { textSize = 48f }
            drawContext.canvas.nativeCanvas.drawText(
                text,
                it.second - paint.measureText(text)/2,
                it.third + shift ,
                paint,
            )
        }
    }
}

@Composable
fun EdgeFromList(
    edgeList: List<Triple<Int, Int, Int>>,
    vertexList: List<Triple<Int, Float, Float>>,
    directed: Boolean
) {
    //TODO("Not yet implemented")
}



@Composable
fun EditTable(
    uiState: UiState,
    addVertex: ()->Unit,
    deleteVertex: (Pair<Float,Float>) -> Unit,
    addEdge: (Pair<Int, Int>) ->Unit,
    deleteEdge: (Pair<Int, Int>) -> Unit,
    onValueChangeOfStart: (Int) -> Unit,
    onValueChangeOfEnd: (Int) -> Unit,
    move: (id: Int, direction: String, distance: Float) ->Unit,
    movingSpeed: Float,
    startQuery: Int,
    endQuery: Int
){
    Row(modifier = Modifier.fillMaxWidth()){

        EditVertex(
            uiState = uiState,
            addVertex = addVertex,
            deleteVertex = deleteVertex,
            move = move,
            movingSpeed = movingSpeed,
        )

        //Divider()


        EditEdge(
            uiState = uiState,
            addEdge = addEdge,
            deleteEdge = deleteEdge,
            onValueChangeOfStart = onValueChangeOfStart,
            onValueChangeOfEnd = onValueChangeOfEnd,
            startQuery = startQuery,
            endQuery = endQuery
        )

    }
}



@Composable
fun EditVertex(
    uiState: UiState,
    addVertex: ()->Unit,
    deleteVertex: (Pair<Float,Float>) -> Unit,
    move: (id: Int, direction: String, distance: Float) ->Unit,
    movingSpeed: Float,
) {
    val vertexList = uiState.vertexList

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Edit vertex",
            fontSize = 20.sp
        )
        LazyColumn(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            items(items = vertexList,
                key = { vertex -> (vertex.hashCode() to "v") }) { vertex ->
                val id = vertexList.indexOf(vertex)
                EachVertex(
                    vertex = vertex,
                    deleteVertex = deleteVertex,
                    id = id,
                    move = move,
                    movingSpeed = movingSpeed,
                )

            }
            item() {
                Button(
                    onClick = addVertex,
                    content = { Text("Add vertex") }
                )

            }

        }
    }
}


@Composable
fun EachVertex(
    vertex: Triple<Int, Float, Float>,
    deleteVertex: (Pair<Float,Float>) -> Unit,
    id: Int,
    move: (id: Int, direction: String, distance: Float) ->Unit,
    movingSpeed: Float,
){

    Card(){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
        ){
            Text( (id + 1).toString())

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,

            ) {
                Button(
                    modifier = Modifier.height(32.dp),
                    onClick = { move(id,"UP", movingSpeed) },
                    content = { Text("↑") }
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ){
                    Button(
                        modifier = Modifier
                            .height(32.dp)
                            .width(60.dp),
                        onClick = { move(id,"LEFT", movingSpeed) },
                        content = { Text("←") }
                    )
                    Button(
                        modifier = Modifier
                            .height(32.dp)
                            .width(60.dp),
                        onClick = { move(id,"RIGHT", movingSpeed) },
                        content = { Text("→") }
                    )
                }
                Button(
                    modifier = Modifier.height(32.dp),
                    onClick = { move(id,"DOWN", movingSpeed) },
                    content = { Text("↓") }
                )

            }


            Button(
                onClick = { deleteVertex(Pair(vertex.second, vertex.third)) },
                content = { Text("Delete") }
            )


        }

    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditEdge(
    uiState: UiState,
    addEdge: (Pair<Int, Int>) ->Unit,
    deleteEdge: (Pair<Int, Int>) -> Unit,
    onValueChangeOfStart: (Int) -> Unit,
    onValueChangeOfEnd: (Int) -> Unit,
    startQuery: Int,
    endQuery: Int
) {


    val edgeList = uiState.edgeList

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Edit edge",
            fontSize = 20.sp
        )

        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            items(items = edgeList,
                key = { edge -> (edge.hashCode() to "e") }
            ) { edge ->
                EachEdge(
                    edge = edge,
                    deleteEdge = deleteEdge
                )
            }

            item() {


                Column() {
                    Row() {

                        OutlinedTextField(
                            value = uiState.startQuery.toString(), //if (startQuery == 0) "" else startQuery.toString(),
                            onValueChange = { newValue: String ->
                                if (newValue.isNotEmpty() && newValue.all { it.isDigit() }) {
                                    onValueChangeOfStart(newValue.toInt())
                                }
                            },
                            singleLine = true,
                            shape = MaterialTheme.shapes.large,
                            modifier = Modifier
                                .width(72.dp)
                                .height(64.dp),
                            label = { Text(text = "Start") },
                            isError = (startQuery <= 0)  && (startQuery == endQuery) &&(startQuery > uiState.vertexList.size),
                            keyboardOptions = KeyboardOptions.Default.copy(
                                imeAction = ImeAction.Next
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = { }
                            )
                        )



                        OutlinedTextField(
                            value = uiState.endQuery.toString(),//if (endQuery == 0) "" else endQuery.toString(),
                            onValueChange = { newValue: String ->
                                if (newValue.isNotEmpty() && newValue.all { it.isDigit() }) {
                                    onValueChangeOfEnd(newValue.toInt())
                                }
                            },
                            singleLine = true,
                            shape = MaterialTheme.shapes.large,
                            modifier = Modifier
                                .width(72.dp)
                                .height(64.dp),
                            label = { Text(text = "End") },
                            isError = (endQuery <= 0) && (startQuery == endQuery) && (endQuery > uiState.vertexList.size),
                            keyboardOptions = KeyboardOptions.Default.copy(
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = { }
                            )
                        )
                    }

                    Button(
                        enabled = (uiState.startQuery > 0) && (uiState.endQuery > 0)
                                && (uiState.startQuery <= uiState.vertexList.size)
                                && (uiState.endQuery <= uiState.vertexList.size)
                                && (uiState.endQuery != uiState.vertexList.size),
                        onClick = { addEdge(uiState.endQuery - 1 to uiState.startQuery - 1) },
                        content = { Text("Add vertex") }
                    )
                    if((uiState.endQuery == uiState.vertexList.size)){
                        Text("Start and End must be different")
                    }
                    if((uiState.startQuery <= 0) || (uiState.startQuery > uiState.vertexList.size)) {
                        Text("Start should be selected from the corresponding vertex range")
                    }
                    if((uiState.endQuery <= 0) || (uiState.endQuery > uiState.vertexList.size)) {
                        Text("End should be selected from the corresponding vertex range")
                    }


                }


            }

        }
    }

 }

@Composable
fun EachEdge(
    edge: Triple<Int, Int, Int>,
    deleteEdge: (Pair<Int, Int>) -> Unit,
){

    Card(){
       Row(verticalAlignment = Alignment.CenterVertically,
           horizontalArrangement = Arrangement.SpaceEvenly,
           modifier = Modifier.fillMaxWidth()
       ){
           Text(text= (edge.second + 1).toString() + " - " + (edge.third + 1).toString())
           Button(
               onClick = {  deleteEdge( Pair(edge.second, edge.third)) },
               content = { Text("Delete") }
           )

       }

    }

}



@Preview
@Composable
fun previewEditTable() {
    val graphViewModel: GraphViewModel = viewModel()
    val uiState = graphViewModel.uiState.collectAsState().value

    EditTable(
        uiState = uiState,
        addVertex = {},
        deleteVertex = {},
        addEdge = {},
        deleteEdge = {},
        onValueChangeOfStart = {},
        onValueChangeOfEnd = {},
        move = { _, _, _ -> {} },
        movingSpeed =10f,
        startQuery = 1,
        endQuery =1
    )
}

