package com.example.flightsearch

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument




@Composable
fun FlightSearchApp(
    queryViewModel: QueryViewModel = viewModel(factory = QueryViewModel.factory),
    favoriteViewModel: FavoriteViewModel = viewModel(factory = FavoriteViewModel.factory)
){
    val airportsList by queryViewModel.getFullAirport().collectAsState(emptyList())
    val favoriteList by favoriteViewModel.getFullFavorite().collectAsState(emptyList())
    Scaffold(
        topBar = {
            FlightSearchTopAppBar()
        }
    ) { innerPadding ->
        { }



        FullQueryScreen(
            airportsList = airportsList,
            contentPadding = innerPadding,
            favoriteClick = { query ->
                {}
            }
        )

    }

}


@Composable
fun FlightSearchTopAppBar() {

    Text("test: top bar")

}






