package com.example.flightsearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.Flow




class FavoriteViewModel(private val favoriteDao: FavoriteDao): ViewModel() {

    fun getFullFavorite(): Flow<List<Favorite>> = favoriteDao.getAll()

    fun addFavorite(favorite: Favorite): Unit = favoriteDao.insert(favorite)
    fun deleteFavorite(favorite: Favorite): Unit = favoriteDao.delete(favorite)


    companion object {
        val factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as AirportSearchApplication)
                FavoriteViewModel(application.database.favoriteDao())
            }
        }
    }
}