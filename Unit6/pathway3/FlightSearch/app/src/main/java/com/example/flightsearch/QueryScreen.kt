package com.example.flightsearch

import android.app.DownloadManager.Query
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Locale

class QueryScreen {
}



@Composable
fun FullQueryScreen(
    airportsList: List<Airport>,
    favoriteClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    QueryScreen(
        airportsList = airportsList,
        favoriteClick = favoriteClick,
        contentPadding = contentPadding,
        modifier = modifier
    )
}



@Composable
fun QueryScreen(
    airportsList: List<Airport>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    query: String? = null,
    favoriteClick: ((String) -> Unit)? = null,
) {
    val queryText = query ?: ""

    val layoutDirection = LocalLayoutDirection.current
    Column(
        modifier = modifier.padding(
            start = contentPadding.calculateStartPadding(layoutDirection),
            end = contentPadding.calculateEndPadding(layoutDirection),
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
            ,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(queryText)

        }
        Divider()
        FlightSearchDetails(
            contentPadding = PaddingValues(
                bottom = contentPadding.calculateBottomPadding()
            ),
            airportsList = airportsList,
            favoriteClick = favoriteClick
        )
    }
}


@Composable
fun FlightSearchDetails(
    airportsList: List<Airport>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    favoriteClick: ((String) -> Unit)? = null
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
    ) {
        items(
            items = airportsList,
            key = { airport -> airport.id }
        ) { airport ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(enabled = favoriteClick != null) {
                        favoriteClick?.invoke(airport.name)
                    }
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (favoriteClick == null) {
                    Text(
                        text = "--",
                        modifier = Modifier.weight(1f)
                    )
                } else {
                    Text(
                        text = airport.name,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontSize = 20.sp,
                            fontWeight = FontWeight(300)
                        )
                    )
                }
                Text(
                    text = "passengers: " +airport.passengers.toString()
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlightSearchTopAppBar(

    modifier: Modifier = Modifier
) {

        TopAppBar(
            title = { Text("Flight Search") },
            modifier = modifier
        )

}




