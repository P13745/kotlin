package com.example.myapplication

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.data.Edge
import com.example.myapplication.data.Vertex


@Composable
fun VertexAndEdgeFromList(
    vertexList: List<Vertex>,
    edgeList: List<Edge>,
    directed: Boolean
) {
    val shift = 60f
    val circleSizePx = with(LocalDensity.current) { (8.dp).toPx() }

    Canvas(
        modifier = Modifier.fillMaxSize()
    ) {
        vertexList.forEach {
            drawCircle(
                color = Color.Blue,
                radius = circleSizePx / 2f,
                center = Offset(it.x, it.y)
            )
            val text = (vertexList.indexOf(it) + 1).toString()
            val paint = android.graphics.Paint().apply { textSize = 48f }
            drawContext.canvas.nativeCanvas.drawText(
                text,
                it.x - paint.measureText(text)/2,
                it.y + shift ,
                paint,
            )
        }

        edgeList.forEach { edge ->


            if(
                edge.startId >= 0
                && edge.startId < vertexList.size
                && edge.endId >=0
                && edge.endId < vertexList.size) {
                val startVertex = vertexList[edge.startId]
                val endVertex = vertexList[edge.endId]

                drawLine(
                    color = Color.Black,
                    start = Offset(startVertex.x, startVertex.y),
                    end = Offset(endVertex.x, endVertex.y),
                    strokeWidth = 2f
                )
            }


        }
    }
}





@Composable
fun EditTable(
    uiState: UiState,
    addVertex: ()->Unit,
    deleteVertex: (Int) -> Unit,
    addEdge: (Pair<Int, Int>) ->Unit,
    deleteEdge: (Pair<Int, Int>) -> Unit,
    onValueChangeOfStart: (Int?) -> Unit,
    onValueChangeOfEnd: (Int?) -> Unit,
    move: (id: Int, direction: String, distance: Float) ->Unit,
    movingSpeed: Float,
    startQuery: Int?,
    endQuery: Int?,
    directed: Boolean,
    loadingOrSaving: Boolean,
    modeChange: (String) -> Unit,
    loadAction: () -> Unit,
    saveAction: () -> Unit,
    loadAction2: () -> Unit,
    saveAction2: () -> Unit,
    loadAction3: () -> Unit,
    saveAction3: () -> Unit,
    emptyOrNot : Boolean,
    emptyOrNot2 : Boolean,
    emptyOrNot3 : Boolean,
){


    Column() {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Button(
                onClick = { modeChange("Vertex") },
                content = { Text("Vertex") }
            )
            Button(
                onClick = { modeChange("Edge") },
                content = { Text("Edge") }
            )
            Button(
                onClick = { modeChange("Save/Load") },
                content = { Text("Save/Load") }
            )


        }

        Box(
            modifier = Modifier.fillMaxSize()
        ) {

            if (uiState.mode == "Vertex" ) {

                VertexList(
                    uiState = uiState,
                    deleteVertex = deleteVertex,
                    move = move,
                    movingSpeed = movingSpeed,
                    loadingOrSaving = loadingOrSaving,
                )

                Button(
                    modifier = Modifier.align(Alignment.BottomEnd),
                    onClick = { modeChange("Edit vertex") },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = MaterialTheme.colorScheme.onSecondary
                    ),
                    content = { Text("+ Edit vertex") },
                    shape = RoundedCornerShape(8.dp)
                )
            }
            if(uiState.mode == "Edit vertex") {
                EditVertex(
                    addVertex = addVertex,
                )
            }



            if (uiState.mode == "Edge") {
                EdgeList(
                    uiState = uiState,
                    deleteEdge = deleteEdge,
                    loadingOrSaving = loadingOrSaving
                )
                Button(
                    modifier = Modifier.align(Alignment.BottomEnd),
                    onClick = { modeChange("Edit edge") },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = MaterialTheme.colorScheme.onSecondary
                    ),
                    content = { Text("+ Edit edge") },
                    shape = RoundedCornerShape(8.dp)
                )
            }

            if(uiState.mode == "Edit edge") {
                EditEdge(
                    uiState = uiState,
                    addEdge = addEdge,
                    onValueChangeOfStart = onValueChangeOfStart,
                    onValueChangeOfEnd = onValueChangeOfEnd,
                    startQuery = startQuery,
                    endQuery = endQuery,
                    directed = directed,
                    loadingOrSaving = loadingOrSaving
                )
            }


            if (uiState.mode == "Save/Load") {
                SaveLoadScreen(
                    loadAction = loadAction,
                    saveAction = saveAction,
                    loadAction2 = loadAction2,
                    saveAction2 = saveAction2,
                    loadAction3 = loadAction3,
                    saveAction3 = saveAction3,
                    modeChange = modeChange,
                    emptyOrNot = emptyOrNot,
                    emptyOrNot2 = emptyOrNot2,
                    emptyOrNot3 = emptyOrNot3,
                    loadingOrSaving = loadingOrSaving,

                    )
            }
        }

    }

}

@Composable
fun SaveLoadScreen(
    loadAction: () -> Unit,
    saveAction: () -> Unit,
    loadAction2: () -> Unit,
    saveAction2: () -> Unit,
    loadAction3: () -> Unit,
    saveAction3: () -> Unit,
    modeChange: (String) -> Unit,
    emptyOrNot : Boolean,
    emptyOrNot2 : Boolean,
    emptyOrNot3 : Boolean,
    loadingOrSaving: Boolean
){
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        Text(
            text = "Save and Load",
            fontSize = 20.sp
        )

        Card() {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Save slot 1")
                Button(
                    content = { Text("Load") },
                    onClick = loadAction,
                    enabled = !loadingOrSaving && !emptyOrNot
                )

                Button(
                    content = { Text("Save") },
                    onClick = saveAction,
                    enabled = !loadingOrSaving
                )
            }
        }
        Spacer(Modifier.height(4.dp))
        Card() {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Save slot 2")
                Button(
                    content = { Text("Load") },
                    onClick = loadAction2,
                    enabled = !loadingOrSaving && !emptyOrNot2
                )

                Button(
                    content = { Text("Save") },
                    onClick = saveAction2,
                    enabled = !loadingOrSaving
                )
            }
        }
        Spacer(Modifier.height(4.dp))
        Card() {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Save slot 3")
                Button(
                    content = { Text("Load") },
                    onClick = loadAction3,
                    enabled = !loadingOrSaving && !emptyOrNot3
                )

                Button(
                    content = { Text("Save") },
                    onClick = saveAction3,
                    enabled = !loadingOrSaving
                )
            }
        }




    }

}




@Composable
fun VertexList(
    uiState: UiState,
    deleteVertex: (Int) -> Unit,
    move: (id: Int, direction: String, distance: Float) ->Unit,
    movingSpeed: Float,
    loadingOrSaving: Boolean,
) {
    val vertexList = uiState.vertexList

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Vertex",
            fontSize = 20.sp
        )
        LazyColumn(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            items(items = vertexList,
                key = { vertex -> vertex.id }) { vertex ->
                val id = vertexList.indexOf(vertex)
                EachVertex(
                    vertex = vertex,
                    deleteVertex = deleteVertex,
                    id = id,
                    move = move,
                    movingSpeed = movingSpeed,
                    loadingOrSaving = loadingOrSaving
                )
                Spacer(Modifier.height(4.dp))

            }


        }
    }
}

@Composable
fun EditVertex(
    addVertex: ()->Unit,
){
    Box(
        modifier = Modifier
            .background(
                color = Color.Gray.copy(alpha = 0.5f)
            )
            .fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(30.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                var vertexNumber by remember { mutableStateOf(1) }


                Box() {
                    var expanded by remember { mutableStateOf(false) }

                    Button(
                        onClick = { expanded = true },
                        modifier = Modifier
                            .padding(16.dp)
                            .height(40.dp)
                            .width(100.dp)
                            .background(Color.White)
                            .border(1.dp, Color.Black, RoundedCornerShape(4.dp)),
                        colors = ButtonDefaults.textButtonColors(contentColor = Color.Black),
                    ) {
                        Text(
                            vertexNumber.toString(),
                            textAlign = TextAlign.Center,
                            overflow = TextOverflow.Clip
                        )
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.background(Color.White),
                        offset = DpOffset(0.dp, 0.dp),
                        properties = PopupProperties(focusable = true)
                    ) {
                        // 選択肢をリストする
                        (1..10).toList().forEach { n ->
                            DropdownMenuItem(
                                onClick = {
                                    vertexNumber = n
                                },
                                text = { Text(n.toString()) }
                            )
                        }
                    }
                }

                var clickCount by remember { mutableStateOf(0) }
                clickCount = vertexNumber

                Button(
                    onClick = {
                        repeat(clickCount) {
                            addVertex()
                        }
                    },
                    content = { Text("Add vertex") }
                )
            }
        }
    }



}


@Composable
fun EachVertex(
    vertex: Vertex,
    deleteVertex: (Int) -> Unit,
    id: Int,
    move: (id: Int, direction: String, distance: Float) ->Unit,
    movingSpeed: Float,
    loadingOrSaving: Boolean,
){

    Card(
        modifier = Modifier.fillMaxWidth(),
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ){
            Text( (id + 1).toString())

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,

            ) {
                Button(
                    modifier = Modifier.height(32.dp),
                    onClick = { move(id,"UP", movingSpeed) },
                    content = { Text("↑") },
                    enabled = !loadingOrSaving
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
                        content = { Text("←") },
                        enabled = !loadingOrSaving
                    )
                    Button(
                        modifier = Modifier
                            .height(32.dp)
                            .width(60.dp),
                        onClick = { move(id,"RIGHT", movingSpeed) },
                        content = { Text("→") },
                        enabled = !loadingOrSaving
                    )
                }
                Button(
                    modifier = Modifier.height(32.dp),
                    onClick = { move(id,"DOWN", movingSpeed) },
                    content = { Text("↓") },
                    enabled = !loadingOrSaving
                )

            }


            Button(
                onClick = { deleteVertex(id) },//====================
                content = { Text("Delete") },
                enabled = !loadingOrSaving
            )


        }

    }

}

@Composable
fun EditEdge(
    uiState: UiState,
    addEdge: (Pair<Int, Int>) ->Unit,
    onValueChangeOfStart: (Int?) -> Unit,
    onValueChangeOfEnd: (Int?) -> Unit,
    startQuery: Int?,
    endQuery: Int?,
    directed: Boolean,
    loadingOrSaving: Boolean,
) {

    Box(
        modifier = Modifier
        .background(
            color = Color.Gray.copy(alpha = 0.5f) // 半透明の薄い灰色
        )
        .fillMaxSize()) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {



            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Box() {
                    var selectedItem by remember { mutableStateOf<String?>(null) }
                    var expanded by remember { mutableStateOf(false) }

                    Button(
                        onClick = { expanded = true },
                        modifier = Modifier
                            .padding(16.dp)
                            .height(40.dp)
                            .width(100.dp)
                            .background(Color.White)
                            .border(1.dp, Color.Black, RoundedCornerShape(4.dp)),
                        colors = ButtonDefaults.textButtonColors(contentColor = Color.Black),
                    ) {
                        Text(
                            startQuery?.toString() ?: "start",
                            textAlign = TextAlign.Center,
                            overflow = TextOverflow.Clip
                        )
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.background(Color.White),
                        offset = DpOffset(0.dp, 0.dp),
                        properties = PopupProperties(focusable = true)
                    ) {
                        // 選択肢をリストする
                        uiState.vertexList.forEach { vertex ->
                            DropdownMenuItem(
                                onClick = {
                                    selectedItem = "${vertex.id + 1}"
                                    expanded = false
                                    onValueChangeOfStart(vertex.id + 1)
                                },
                                text = { Text("${vertex.id + 1}") }
                            )
                        }
                    }
                }

                Text("-")

                Box() {
                    var selectedItem by remember { mutableStateOf<String?>(null) }
                    var expanded by remember { mutableStateOf(false) }

                    Button(
                        onClick = { expanded = true },
                        modifier = Modifier
                            .padding(16.dp)
                            .height(40.dp)
                            .width(100.dp)
                            .background(Color.White)
                            .border(1.dp, Color.Black, RoundedCornerShape(4.dp)),
                        colors = ButtonDefaults.textButtonColors(contentColor = Color.Black),
                    ) {
                        Text(
                            endQuery?.toString() ?: "end",
                            textAlign = TextAlign.Center,
                            overflow = TextOverflow.Clip
                        )
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.background(Color.White),
                        offset = DpOffset(0.dp, 0.dp),
                        properties = PopupProperties(focusable = true)
                    ) {
                        // 選択肢をリストする
                        uiState.vertexList.forEach { vertex ->
                            DropdownMenuItem(
                                onClick = {
                                    selectedItem = "${vertex.id + 1}"
                                    expanded = false
                                    onValueChangeOfEnd(vertex.id + 1)
                                },
                                text = { Text("${vertex.id + 1}") }
                            )
                        }
                    }
                }

            }

            var enabled by remember { mutableStateOf(false) }
            enabled = !loadingOrSaving
                    && (uiState.startQuery != null) && (uiState.endQuery != null)
                    && (uiState.startQuery > 0) && (uiState.endQuery > 0)
                    && (uiState.startQuery <= uiState.vertexList.size)
                    && (uiState.endQuery <= uiState.vertexList.size)
                    && (uiState.endQuery != uiState.startQuery)
                    && (uiState.edgeList.filter { edge ->
                !(Pair(edge.startId, edge.endId) != Pair(
                    uiState.startQuery - 1,
                    uiState.endQuery - 1
                )
                        && (Pair(edge.endId, edge.startId) != Pair(
                    uiState.startQuery - 1,
                    uiState.endQuery - 1
                ) || directed))
            }.toMutableList().size == 0)

            var showDialog by remember { mutableStateOf(false) }

            Button(
                onClick = {
                    if (enabled) {
                        if ((uiState.startQuery != null) && (uiState.endQuery != null)) {
                            addEdge(uiState.endQuery - 1 to uiState.startQuery - 1)
                        }
                        onValueChangeOfStart(null)
                        onValueChangeOfEnd(null)
                    } else {
                        showDialog = true
                    }
                },
                content = { Text("Add vertex") }
            )
            if ((uiState.startQuery != null) && (uiState.endQuery != null)
                && (uiState.edgeList.filter { edge ->
                    !(Pair(edge.startId, edge.endId) != Pair(
                        uiState.startQuery - 1,
                        uiState.endQuery - 1
                    )
                            && (Pair(edge.endId, edge.startId) != Pair(
                        uiState.startQuery - 1,
                        uiState.endQuery - 1
                    ) || directed))
                }.toMutableList().size > 0)
            ) {
                Text("The edge already exists (directed? : ${uiState.directed})")
            }
            if ((uiState.endQuery != null) && (uiState.endQuery == uiState.startQuery)) {
                Text("Start and End must be different")
            }
            if ((uiState.startQuery != null) && ((uiState.startQuery <= 0) || (uiState.startQuery > uiState.vertexList.size))) {
                Text("Start should be selected from the corresponding vertex range")
            }
            if ((uiState.endQuery != null) && ((uiState.endQuery <= 0) || (uiState.endQuery > uiState.vertexList.size))) {
                Text("End should be selected from the corresponding vertex range")
            }


            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text("Alert") },
                    text = {
                        Column {
                            if(uiState.startQuery == null){
                                Text("'Start' must be entered")
                            }
                            if(uiState.endQuery == null){
                                Text("'End' must be entered")
                            }
                            if ((uiState.startQuery != null) && (uiState.endQuery != null)
                                && (uiState.edgeList.filter { edge ->
                                    !(Pair(edge.startId, edge.endId) != Pair(
                                        uiState.startQuery - 1,
                                        uiState.endQuery - 1
                                    )
                                            && (Pair(edge.endId, edge.startId) != Pair(
                                        uiState.startQuery - 1,
                                        uiState.endQuery - 1
                                    ) || directed))
                                }.toMutableList().size > 0)
                            ) {
                                Text("The edge already exists (directed? : ${uiState.directed})")
                            }
                            if ((uiState.endQuery != null) && (uiState.endQuery == uiState.startQuery)) {
                                Text("'Start' and 'End' must be different")
                            }
                            if ((uiState.startQuery != null) && ((uiState.startQuery <= 0) || (uiState.startQuery > uiState.vertexList.size))) {
                                Text("'Start' should be selected from the corresponding vertex range")
                            }
                            if ((uiState.endQuery != null) && ((uiState.endQuery <= 0) || (uiState.endQuery > uiState.vertexList.size))) {
                                Text("'End' should be selected from the corresponding vertex range")
                            }
                        }
                    },
                    confirmButton = {
                        Button(
                            onClick = { showDialog = false },
                            colors = ButtonDefaults.buttonColors()
                        ) {
                            Text("OK")
                        }
                    }
                )
            }


        }
    }
}



@Composable
fun EdgeList(
    uiState: UiState,
    deleteEdge: (Pair<Int, Int>) -> Unit,
    loadingOrSaving: Boolean,
) {
    val edgeList = uiState.edgeList

    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Text(
            text = "Edge",
            fontSize = 20.sp
        )

        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            items(items = edgeList,
                key = { edge -> edge.id }
            ) { edge ->
                EachEdge(
                    edge = edge,
                    deleteEdge = deleteEdge,
                    loadingOrSaving = loadingOrSaving
                )
                Spacer(Modifier.height(4.dp))
            }

        }

    }
}



@Composable
fun EachEdge(
    edge: Edge,
    deleteEdge: (Pair<Int, Int>) -> Unit,
    loadingOrSaving: Boolean
){

    Card(){
       Row(verticalAlignment = Alignment.CenterVertically,
           horizontalArrangement = Arrangement.SpaceEvenly,
           modifier = Modifier.fillMaxWidth()
       ){
           Text(text= (edge.startId + 1).toString() + " - " + (edge.endId + 1).toString())
           Button(
               onClick = {  deleteEdge( Pair(edge.startId, edge.endId)) },
               content = { Text("Delete") },
               enabled = !loadingOrSaving
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
        endQuery =1,
        directed = false,
        loadingOrSaving = false ,
        modeChange = {},
        loadAction = {},
        saveAction = {},
        loadAction2 = {},
        saveAction2 = {},
        loadAction3 = {},
        saveAction3 = {},
        emptyOrNot = false,
        emptyOrNot2 = false,
        emptyOrNot3 = false,
    )
}

