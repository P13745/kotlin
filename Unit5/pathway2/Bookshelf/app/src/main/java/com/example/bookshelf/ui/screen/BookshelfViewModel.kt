package com.example.bookshelf.ui.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.bookshelf.BookshelfApplication
import com.example.bookshelf.data.BookshelfDataRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface BookshelfUiState {
    data class Success( val data: List<String>) : BookshelfUiState
    object Error : BookshelfUiState
    object Loading : BookshelfUiState
}

class BookshelfViewModel(private val bookshelfDataRepository: BookshelfDataRepository): ViewModel() {

    var bookshelfUiState: BookshelfUiState by mutableStateOf(BookshelfUiState.Loading)
        private set


    init {
        getBookshelfData()
    }


    fun getBookshelfData() {
        viewModelScope.launch {
            bookshelfUiState = BookshelfUiState.Success(bookshelfDataRepository.getBookshelfData())
            /*bookshelfUiState = BookshelfUiState.Loading
            bookshelfUiState = try {
                BookshelfUiState.Success(bookshelfDataRepository.getBookshelfData())

            } catch (e: IOException) {
                BookshelfUiState.Error
            } catch (e: HttpException) {
                BookshelfUiState.Error
            }

             */


        }
    }


    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BookshelfApplication)

                val bookshelfDataRepository = application.container.BookshelfDataRepository
                BookshelfViewModel(bookshelfDataRepository = bookshelfDataRepository)
            }
        }
    }








}
