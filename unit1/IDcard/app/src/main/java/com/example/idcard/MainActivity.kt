package com.example.idcard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.idcard.ui.theme.IDcardTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IDcardTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFDBF8D9)
                    //MaterialTheme.colorScheme.background

                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {


                        Column(modifier = Modifier
                            .fillMaxWidth()
                            .height(800.dp)
                            .padding(16.dp)
                            .weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {


                            val image = painterResource(R.drawable.android_logo)
                            Image(
                                painter = image,
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(top = 128.dp)
                                    .background(color = Color(0xFF129AAC))
                                    .size(128.dp)
                            )

                            Text(
                                text = "Ryotaro Kosuge",
                                color = Color(0xFF225A22),
                                fontSize = 40.sp
                            )
                            Text(
                                text = "Android Development Extraordinaire",
                                color = Color(0xFF225A22),
                                fontSize = 16.sp
                            )
                        }


                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(top = 128.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                IconAndInfo(R.drawable.discord, ": example#1234")
                                IconAndInfo(R.drawable.mail_icon, ": example@gmail.com")
                                IconAndInfo(R.drawable.phone_icon01, ": 000-0000-0000")
                            }
                        }


                    }
                    //comment
                }
            }
        }
    }
}

@Composable
fun IconAndInfo(icon: Int, info: String) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    )
    {
        Image(
            painter = painterResource(icon),
            contentDescription = null,
            modifier = Modifier.size(32.dp)
        )

        Box(modifier = Modifier.width(16.dp)){}

        Text(
            text = info,
            fontSize = 20.sp
        )


    }
}

