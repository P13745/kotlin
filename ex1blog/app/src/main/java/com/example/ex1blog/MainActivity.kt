package com.example.ex1blog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ex1blog.ui.theme.Ex1blogTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Ex1blogTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        verticalArrangement = Arrangement.Top
                    ) {
                        val image = painterResource(R.drawable.bg_compose_background)
                        Image(
                            painter = image,
                            contentDescription = null
                        )

                        Text(
                            stringResource(R.string.text1),
                            Modifier
                            .padding(16.dp),
                            fontSize = 24.sp
                        )
                        Text(
                            stringResource(R.string.text2),
                            Modifier
                            .padding(start = 16.dp, end = 16.dp),
                            textAlign = TextAlign.Justify
                        )
                        Text(stringResource(R.string.text3),
                            Modifier
                            .padding(16.dp),
                            textAlign = TextAlign.Justify
                        )
                    }

                }
            }
        }
    }
}




@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Ex1blogTheme {
        Greeting("Android")
    }
}