package com.example.dessertclicker.ui


import com.example.dessertclicker.data.Datasource.dessertList
import com.example.dessertclicker.model.Dessert

data class GameUiState (
    var revenue: Int = 0,
    var dessertsSold: Int = 0,
    val currentDessertIndex: Int = 0,
    var currentDessert: Dessert = dessertList[currentDessertIndex],
    //var nextDessert: Dessert = dessertList[1],
    var currentDessertPrice: Int= dessertList[currentDessertIndex].price,
    var currentDessertImageId: Int =  dessertList[currentDessertIndex].imageId,

)