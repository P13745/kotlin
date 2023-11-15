package com.example.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artspace.ui.theme.ArtSpaceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtSpaceTheme {
                ArtSpaceApp()

            }
        }
    }
}


@Composable
fun ArtSpaceApp(){
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
        ){

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            var pageNumber by remember { mutableStateOf(1) }
            val numberOfWorks = ArtWork.numberOfWorks


            Box(
                modifier = Modifier
                    .wrapContentWidth()
                    .background(
                        color = Color(0xFFE9EEED),
                        shape = RoundedCornerShape(4.dp)
                    )

            ) {
                Image(
                    painter = painterResource(id = ArtWork(pageNumber).painterId),
                    contentDescription = null,
                    modifier = Modifier
                        .width(360.dp)
                        .height(360.dp)
                        .padding(8.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Column(
                modifier = Modifier
                    .width(360.dp)
                    .height(80.dp)
                    .background(
                        color = Color(0xFFE9EEED),
                        shape = RoundedCornerShape(4.dp)
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    stringResource(ArtWork(pageNumber).titleId),
                    fontSize = 24.sp
                )

                Text(
                    "(" + ArtWork(pageNumber).year + ")",
                    fontSize = 12.sp
                )

                Text(
                    stringResource(ArtWork(pageNumber).textId),
                    fontSize = 16.sp
                )
            }
            Spacer(modifier = Modifier.height(20.dp))

            Row (
                horizontalArrangement = Arrangement.SpaceEvenly ,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Button(
                    onClick = {
                        if (pageNumber <= 1 || pageNumber > numberOfWorks) {
                            pageNumber = numberOfWorks
                        } else {
                            pageNumber--
                        }
                    },
                    shape = RectangleShape,
                    modifier = Modifier
                        .width(128.dp)
                        .height(32.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFA4BEB9))
                ) {
                    if(pageNumber != 1){ Text(stringResource(R.string.previous)) }
                    else{ Text(stringResource(R.string.last)) }
                }

                Spacer(modifier = Modifier. width(20.dp))

                Button(
                    onClick = {
                        if (pageNumber >= numberOfWorks || pageNumber < 1) {
                            pageNumber = 1
                        } else {
                            pageNumber++
                        }
                    },
                    shape = RectangleShape,
                    modifier = Modifier
                        .width(128.dp)
                        .height(32.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFA4BEB9))
                ) {
                    if(pageNumber != numberOfWorks){ Text(stringResource(R.string.next)) }
                    else{ Text(stringResource(R.string.back)) }
                }
            }

        }

    }
}

class ArtWork(id: Int) {
    companion object {
        const val numberOfWorks = 6
    }

    val painterId: Int = when (id){
        1 -> R.drawable.img_7795
        2 -> R.drawable.img_8785
        3 -> R.drawable.img_8660
        4 -> R.drawable.img_1490
        5 -> R.drawable.img_1257
        6 -> R.drawable.img_9422
        else -> R.drawable.error
    }
    val titleId: Int = when (id){
        1 -> R.string.title1
        2 -> R.string.title2
        3 -> R.string.title3
        4 -> R.string.title4
        5 -> R.string.title5
        6 -> R.string.title6
        else -> R.string.error
    }
    val textId: Int = when (id){
        1 -> R.string.text1
        2 -> R.string.text2
        3 -> R.string.text3
        4 -> R.string.text4
        5 -> R.string.text5
        6 -> R.string.text6
        else -> R.string.error
    }

    val year: String = when (id){
        1 -> "2019/12/22"
        2 -> "2020/7/07"
        3 -> "2020/06/06"
        4 -> "2022/12/09"
        5 -> "2022/08/20"
        6 -> "2021/03/10"
        else -> "NO DATA"
    }
}