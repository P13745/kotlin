package com.example.flightsearch

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.room.ColumnInfo


@Composable
fun FlightSearchApp(
    queryViewModel: QueryViewModel = viewModel(factory = QueryViewModel.factory),
    favoriteViewModel: FavoriteViewModel = viewModel(factory = FavoriteViewModel.factory),
    queryWordViewModel: QueryWordViewModel = viewModel()
){
    //val airportsList by queryViewModel.getFullAirport().collectAsState(emptyList())
    val airportsList by queryViewModel.getAirportFor(queryWordViewModel.nowQueryWord).collectAsState(emptyList())
    val airportsList2 by queryViewModel.getAirportFor(queryWordViewModel.nowQueryWord2).collectAsState(emptyList())
    val favoriteList by favoriteViewModel.getFullFavorite().collectAsState(emptyList())
    val fullList by queryViewModel.getFullAirport().collectAsState(emptyList())

    queryWordViewModel
    Scaffold(
        topBar = {
            FlightSearchTopAppBar()
        }
    ) { innerPadding ->
        Column() {
            Spacer(modifier = Modifier.height(50.dp))


            Screen().FullQueryScreen(
                fullList = fullList,
                airportsList = airportsList,
                airportsList2 = airportsList2,
                favoriteList = favoriteList,
                contentPadding = innerPadding,
                favoriteClick = { query ->
                    {}
                },
                favoriteViewModel = favoriteViewModel,
                queryWordViewModel = queryWordViewModel,
                onClickDeparture = { queryWordViewModel.setDepature(it) },
                onClickDestination = { queryWordViewModel.setDestination(it) }
            )
        }

    }

}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlightSearchTopAppBar(
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        title = { Text("Flight Search") },
        modifier = modifier,
        )

}






