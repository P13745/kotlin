package com.example.mycity.ui

import com.example.mycity.data.Category
import com.example.mycity.data.Location
import com.example.mycity.data.LocationData.locationList

data class MyCityUiState (
    val currentCategory: Category = Category.Nature,
    val currentSelectedLocation: Location = locationList[0],
    val isShowingHomepage: Boolean = true,
    val isShowingCategory: Boolean = false,
    val isShowingDetails: Boolean = false
)