package com.example.courses

import Topic
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.courses.data.DataSource
import com.example.courses.ui.theme.CoursesTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CoursesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CoursesApp()
                }
            }
        }
    }
}



@Composable
fun Cards(
    iconId: Int,
    bodyMediumId: Int,
    labelMediumId: Int,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier.padding(top = 8.dp)) {
        Row(
            modifier = Modifier
                .height(68.dp)
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(iconId),
                contentDescription = null,
                modifier = Modifier
                    .height(68.dp)
                    .width(68.dp),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .height(68.dp)
                    .fillMaxWidth()
            ) {
                Box(modifier.padding(start =16.dp, top = 16.dp, end =8.dp) ){
                    Text(
                        text = stringResource(bodyMediumId),
                        fontSize = 14.sp
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))

                Row(modifier = Modifier.fillMaxHeight()) {
                    Spacer(modifier = Modifier.width(16.dp))
                    Image(
                        painter = painterResource(id = R.drawable.ic_grain),
                        contentDescription = null
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Text(
                        text = labelMediumId.toString(),
                        fontSize = 10.sp
                    )
                }
            }




        }
    }
}
@Composable
fun CardsForTopic(topic: Topic, modifier: Modifier){
    Cards(
        topic.imageResourceId,
        topic.stringResourceId,
        topic.number,
        modifier = modifier
    )

}




@Composable
fun CoursesApp() {
    val leftOrRight = DataSource.topics.groupBy { DataSource.topics.indexOf(it) % 2 }

    Row(
        modifier = Modifier
            .fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp, end = 4.dp)
        ) {
            items(
                count = leftOrRight[0]!!.size,
                key = { index -> leftOrRight[0]!![index].hashCode() }
            ) {
                    index ->
                CardsForTopic(
                    leftOrRight[0]!![index],
                    modifier = Modifier
                )
            }



        }

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(start = 4.dp, end = 8.dp)
        ) {
            items(
                count = leftOrRight[1]!!.size,
                key = { index -> leftOrRight[1]!![index].hashCode() }
            ) {
                    index ->
                CardsForTopic(
                    leftOrRight[1]!![index],
                    modifier = Modifier
                )
            }

        }

    }
}


@Preview(showBackground = true)
@Composable
fun PreviewCards() {
    Surface {

        CoursesApp()

    }
}