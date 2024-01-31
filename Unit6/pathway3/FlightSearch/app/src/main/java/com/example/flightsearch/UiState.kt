package com.example.flightsearch

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class UiState(
    val nowQueryWord: String = "",
    val nowQueryWord2: String = "",
    val departure: String = "",
    val destination: String = "",

    )

class QueryWordViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun reset() {
        _uiState.value = UiState()
        nowQueryWord = ""
    }

    var nowQueryWord by mutableStateOf("")
        private set
    fun updateQuery(query: String){
        nowQueryWord = query
    }

    var nowQueryWord2 by mutableStateOf("")
        private set
    fun updateQuery2(query2: String){
        nowQueryWord2 = query2
    }

    fun setDepature(airport: Airport?) {
        val currentUiState = _uiState.value
        if (airport != null) {
            _uiState.value = currentUiState.copy(departure = airport.iataCode)
        } else {
            _uiState.value = currentUiState.copy(departure = "")
        }
    }
    fun setDestination(airport: Airport?) {
        val currentUiState = _uiState.value
        if (airport != null) {
            _uiState.value = currentUiState.copy(destination = airport.iataCode)
        } else {
            _uiState.value = currentUiState.copy(destination = "")
        }
    }


}