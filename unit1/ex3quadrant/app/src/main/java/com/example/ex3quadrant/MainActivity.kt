package com.example.ex3quadrant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ex3quadrant.ui.theme.Ex3quadrantTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Ex3quadrantTheme {
                // A surface container using the 'background' color from the theme

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FourBlocks()
                }
            }
        }
    }
}




@Composable
fun FourBlocks() {
    Column(Modifier.fillMaxWidth()) {
        Row(Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            CardBlock(
                title = stringResource(R.string.title1),
                text = stringResource(R.string.text1),
                color = Color(0xFFEADDFF),
                modifier = Modifier.weight(1f)
            )
            CardBlock(
                title = stringResource(R.string.title2),
                text = stringResource(R.string.text2),
                color = Color(0xFFD0BCFF),
                modifier = Modifier.weight(1f)
            )
        }

        Row(Modifier.weight(1f),
        verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
            ) {
            CardBlock(
                title = stringResource(R.string.title3),
                text = stringResource(R.string.text3),
                color = Color(0xFFB69DF8),
                modifier = Modifier.weight(1f)
            )
            CardBlock(
                title = stringResource(R.string.title4),
                text = stringResource(R.string.text4),
                color = Color(0xFFF6EDFF),
                modifier = Modifier.weight(1f)
            )
        }
    }

}



@Composable
fun CardBlock(title: String, text: String, color: Color, modifier: Modifier) {


        Column(
            modifier = modifier
                .fillMaxSize()
                .background(color)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp),
                fontWeight = FontWeight.Bold
            )
            Text(
                text = text,
                textAlign = TextAlign.Justify
            )
        }


}


