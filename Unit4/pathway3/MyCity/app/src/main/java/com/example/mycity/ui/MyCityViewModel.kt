package com.example.mycity.ui

import androidx.lifecycle.ViewModel
import com.example.mycity.data.Category
import com.example.mycity.data.Location
import com.example.mycity.data.LocationData.locationList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class MyCityViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MyCityUiState())
    val uiState: StateFlow<MyCityUiState> = _uiState

    fun updateDetailsScreenStates(location: Location) {
        _uiState.update {
             it.copy(
                currentSelectedLocation = location,
                currentCategory = location.category,
                 isShowingDetails = true,
                 isShowingCategory = true
            )
        }
    }

    fun updateCategoryScreenStates(category: Category) {
        _uiState.update {
            it.copy(
                currentSelectedLocation = locationList.filter { it -> it.category == category }[0],
                currentCategory = category,
                isShowingCategory = true
            )
        }
    }

    fun backToEachCategory() {
        _uiState.update {
            it.copy(
                isShowingDetails = false,
                currentSelectedLocation = locationList.filter { it -> it.category == MyCityUiState().currentCategory }[0]
            )
        }
    }
    fun backToHome() {
        _uiState.update {
            it.copy(
                isShowingDetails = false,
                isShowingCategory = false,
                currentCategory = Category.Nature,
                currentSelectedLocation = locationList[0],

            )

        }
    }









}