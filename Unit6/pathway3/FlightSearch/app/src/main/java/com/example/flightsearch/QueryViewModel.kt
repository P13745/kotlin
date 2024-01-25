package com.example.flightsearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.Flow

class QueryViewModel(private val airportDao: AirportDao): ViewModel() {

    fun getFullAirport(): Flow<List<Airport>> = airportDao.getAll()
    fun getAirportFor(query: String): Flow<List<Airport>> = airportDao.getFromQuery(query)

    companion object {
        val factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as AirportSearchApplication)
                QueryViewModel(application.database.airportDao())
            }
        }
    }
}