package com.example.lemonade


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lemonade.ui.theme.LemonadeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LemonadeTheme {
                LemonApp()
            }
        }
    }
}


@Composable
fun LemonApp() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {




        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            var result by remember { mutableStateOf(1) }
            var squeezeCounter by remember { mutableStateOf(0) }
            if(result == 1) { squeezeCounter = (2..4).random() }



            val (painterId, textId) = when (result){
                    1 ->  Pair(R.drawable.lemon_tree, R.string.text1)
                    2 ->  Pair(R.drawable.lemon_squeeze, R.string.text2)
                    3 ->  Pair(R.drawable.lemon_drink, R.string.text3)
                    else ->  Pair(R.drawable.lemon_restart, R.string.text4)
                }


            Column(verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
            ) {


                Button(
                    onClick = {
                        if (result == 4) {
                            result = 1
                        } else if (result == 2 && squeezeCounter > 0) {
                            squeezeCounter--
                        } else {
                            result++
                        }
                    },
                    shape = RoundedCornerShape(32.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFA6E9D1))
                ) {
                    Image(
                        painter = painterResource(id = painterId),
                        contentDescription = null,
                        modifier = Modifier
                            .width(240.dp)
                            .height(258.dp)
                            .padding(16.dp)
                    )
                }

                Spacer(
                    modifier = Modifier.height(16.dp)
                )


                //Text(squeezeCounter.toString())
                Text(
                    stringResource(id = textId),
                    fontSize = 18.sp,
                )


            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LemonadeTheme {
        LemonApp()
    }
}