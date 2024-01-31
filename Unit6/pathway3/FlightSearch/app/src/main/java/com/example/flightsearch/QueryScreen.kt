package com.example.flightsearch

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.lifecycle.ViewModel
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class QueryScreen(airportsList: List<Airport>, favoriteViewModel: Any) {
}


class Screen {
    private val coroutineScope: CoroutineScope = MainScope()

    @Composable
    fun FullQueryScreen(
        fullList: List<Airport>,
        airportsList: List<Airport>,
        airportsList2: List<Airport>,
        favoriteList: List<Favorite>,
        favoriteClick: (Favorite) -> Unit,
        onClickDeparture: (Airport) -> Unit,
        onClickDestination: (Airport) -> Unit,
        modifier: Modifier = Modifier,
        contentPadding: PaddingValues = PaddingValues(0.dp),
        queryWordViewModel: QueryWordViewModel,
        favoriteViewModel: FavoriteViewModel,

        ) {

        val uiState by queryWordViewModel.uiState.collectAsState()
        val keyboardController = LocalSoftwareKeyboardController.current



        Column(modifier = Modifier.padding(8.dp),) {
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "departure : " + uiState.departure)
                    Text(text = "destination : " + uiState.destination)
                }

                val foundFavorite: Favorite? = favoriteList.find { favorite ->
                    favorite.departureCode == uiState.departure
                            && favorite.destinationCode == uiState.destination
                            && uiState.departure != uiState.destination
                }
                Spacer(modifier = Modifier.weight(1f).fillMaxWidth())

                if (foundFavorite == null) {
                    Button(
                        enabled = (uiState.departure != "") && (uiState.destination != "")&&(uiState.departure != uiState.destination),
                        onClick = {
                            coroutineScope.launch {
                                favoriteViewModel.addFavorite(
                                    Favorite(
                                        id = 0,
                                        departureCode = uiState.departure,
                                        destinationCode = uiState.destination
                                    )
                                )
                            }
                        },
                        content = { Text("Add to Favorites List") }
                    )
                } else {
                    Button(
                        enabled = (uiState.departure != "") && (uiState.destination != ""),
                        onClick = {
                            coroutineScope.launch {
                                favoriteViewModel.deleteFavorite(foundFavorite)
                            }
                                  },
                        content = { Text("Remove from Favorites List") }
                    )
                }


            }

            if ((uiState.departure != "") && (uiState.destination != "") &&(uiState.departure == uiState.destination)) {
                Text (text = "Departure and arrival airports must be different!")
            } else {
                Text("")
            }




            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = queryWordViewModel.nowQueryWord,
                singleLine = true,
                shape = MaterialTheme.shapes.large,
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    disabledContainerColor = MaterialTheme.colorScheme.surface,
                ),
                onValueChange = {
                    queryWordViewModel.reset()
                    queryWordViewModel.updateQuery(it)
                },
                label = {
                    if (uiState.nowQueryWord == "") {
                        Text(text = "Departure : Enter airport name of IATA code")
                    } else {
                        Text(text = uiState.nowQueryWord)
                    }
                },
                isError = false,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { }
                )
            )

            Spacer(modifier = Modifier.height(8.dp))


            if (uiState.departure == "") {
                queryWordViewModel.setDestination(null)
                queryWordViewModel.updateQuery2("")


                if (queryWordViewModel.nowQueryWord == "") {
                    FavoriteScreen(
                        fullList = fullList,
                        favoriteList = favoriteList,
                        favoriteClick = favoriteClick,
                        contentPadding = contentPadding,
                        modifier = modifier,
                        favoriteViewModel = favoriteViewModel
                    )
                } else {
                    QueryScreen(
                        airportsList = airportsList,
                        onClick = onClickDeparture,
                        contentPadding = contentPadding,
                        modifier = modifier,
                        favoriteViewModel = favoriteViewModel,
                    )
                }
            } else {
                Text(text = "departure : ${uiState.departure}")


                if (uiState.departure != "") {

                    OutlinedTextField(
                        value = queryWordViewModel.nowQueryWord2,
                        singleLine = true,
                        shape = MaterialTheme.shapes.large,
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.surface,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                            disabledContainerColor = MaterialTheme.colorScheme.surface,
                        ),
                        onValueChange = {
                            queryWordViewModel.updateQuery2(it)
                        },
                        label = {
                            if (uiState.nowQueryWord2 == "") {
                                Text(text = "Destination : Enter airport name of IATA code")
                            } else {
                                Text(text = uiState.nowQueryWord2)
                            }
                        },
                        isError = false,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = { keyboardController?.hide() }
                        )
                    )

                    QueryScreen(
                        airportsList = airportsList2,
                        onClick = onClickDestination,
                        contentPadding = contentPadding,
                        modifier = modifier,
                        favoriteViewModel = favoriteViewModel,
                    )
                } else {

                }
                Text(text = "destination : ${uiState.destination}")
            }
        }
    }


    @Composable
    fun QueryScreen(
        airportsList: List<Airport>,
        modifier: Modifier = Modifier,
        contentPadding: PaddingValues = PaddingValues(0.dp),
        query: String? = null,
        onClick: ((Airport) -> Unit)? = null,
        favoriteViewModel: FavoriteViewModel,
    ) {


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
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {


            }
            Divider()
            FlightSearchDetails(
                contentPadding = PaddingValues(
                    bottom = contentPadding.calculateBottomPadding()
                ),
                airportsList = airportsList,
                onClick = onClick
            )
        }
    }


    @Composable
    fun FlightSearchDetails(
        airportsList: List<Airport>,
        modifier: Modifier = Modifier,
        contentPadding: PaddingValues = PaddingValues(0.dp),
        onClick: ((Airport) -> Unit)? = null,
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
                        .clickable(enabled = onClick != null) {
                            onClick?.invoke(airport)
                        }
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    if (onClick == null) {
                        Text(
                            text = "--",
                            modifier = Modifier.weight(1f)
                        )
                    } else {
                        Text(
                            text = airport.iataCode,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontSize = 16.sp,
                                fontWeight = FontWeight(800)
                            )
                        )
                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = airport.name,
                            maxLines = 1,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontSize = 16.sp,
                                fontWeight = FontWeight(300),
                            )
                        )
                    }
                    /*Text(
                    text = "passengers: " +airport.passengers.toString()
                )

                 */
                }
            }
        }
    }


    @Composable
    fun FavoriteScreen(
        fullList: List<Airport>,
        favoriteList: List<Favorite>,
        modifier: Modifier = Modifier,
        contentPadding: PaddingValues = PaddingValues(0.dp),
        //query: String? = null,
        favoriteClick: ((Favorite) -> Unit)? = null,
        favoriteViewModel: FavoriteViewModel,
    ) {


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
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {


            }
            Divider()
            Text(text = "Favorited : ")
            if (favoriteList.isEmpty()) {
                Text(text = "   No favorites to show")
            } else {
                FavoriteList(
                    favoriteViewModel = favoriteViewModel,
                    fullList = fullList,
                    contentPadding = PaddingValues(
                        bottom = contentPadding.calculateBottomPadding()
                    ),
                    favoriteList = favoriteList,
                    favoriteClick = favoriteClick
                )
            }
        }
    }

    @Composable
    fun FavoriteList(
        favoriteViewModel : FavoriteViewModel,
        fullList: List<Airport>,
        favoriteList: List<Favorite>,
        modifier: Modifier = Modifier,
        contentPadding: PaddingValues = PaddingValues(0.dp),
        favoriteClick: ((Favorite) -> Unit)? = null
    ) {
        LazyColumn(
            modifier = modifier,
            contentPadding = contentPadding,
        ) {
            items(
                items = favoriteList,
                key = { favorite -> favorite.id }
            ) { favorite ->

                val foundDepartureName: Airport? = fullList.find { airport ->
                    airport.iataCode == favorite.departureCode
                }
                val departureName = foundDepartureName?.name ?: ""

                val foundDestinationName: Airport? = fullList.find { airport ->
                    airport.iataCode == favorite.destinationCode
                }
                val destinationName = foundDestinationName?.name ?: ""



                Card() {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(enabled = favoriteClick != null) {
                                favoriteClick?.invoke(favorite)
                            }
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        if (favoriteClick == null) {
                            Text(
                                text = "--",
                                modifier = Modifier.weight(1f)
                            )
                        } else {
                            Column() {
                                Row()
                                {
                                    Text(
                                        text = favorite.departureCode,
                                        style = MaterialTheme.typography.bodyLarge.copy(
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight(800)
                                        )
                                    )
                                    Text(
                                        text = " : $departureName",
                                        style = MaterialTheme.typography.bodyLarge.copy(
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight(200)
                                        )
                                    )
                                }
                                Row() {
                                    Text(
                                        text = favorite.destinationCode,
                                        style = MaterialTheme.typography.bodyLarge.copy(
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight(800)
                                        )
                                    )
                                    Text(
                                        text = " : $destinationName",
                                        style = MaterialTheme.typography.bodyLarge.copy(
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight(200)
                                        )
                                    )
                                }

                                Row(modifier = Modifier.fillMaxWidth()) {
                                    Spacer(Modifier.weight(1f))
                                    Button(
                                        onClick = {
                                            coroutineScope.launch {
                                                favoriteViewModel.deleteFavorite(favorite)
                                            }
                                        },
                                        content = { Text("Remove from Favorites List") }
                                    )
                                }

                            }
                        }

                    }
                }
            }
        }
    }





}



