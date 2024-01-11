/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.marsphotos.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.marsphotos.MarsPhotosApplication
import com.example.marsphotos.data.MarsPhotosRepository
import com.example.marsphotos.data.NetworkMarsPhotosRepository
import com.example.marsphotos.model.MarsPhoto
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

/**
 * UI state for the Home screen
 */
sealed interface MarsUiState {
    data class Success(val photos: String) : MarsUiState
    object Error : MarsUiState
    object Loading : MarsUiState
}

class MarsViewModel(
    private val marsPhotosRepository: MarsPhotosRepository
) : ViewModel() {
    /** The mutable State that stores the status of the most recent request */
    var marsUiState: MarsUiState by mutableStateOf(MarsUiState.Loading)
        private set

    /**
     * Call getMarsPhotos() on init so we can display status immediately.
     */
    init {
        getMarsPhotos()
    }

    /**
     * Gets Mars photos information from the Mars API Retrofit service and updates the
     * [MarsPhoto] [List] [MutableList].
     */
    fun getMarsPhotos() {
        viewModelScope.launch {
            marsUiState = MarsUiState.Loading
            marsUiState = try {
                //val listResult = MarsApi.retrofitService.getPhotos()
                //val marsPhotosRepository = NetworkMarsPhotosRepository()
                val listResult = marsPhotosRepository.getMarsPhotos()
                MarsUiState.Success(
                    "Success: ${listResult.size} Mars photos retrieved"
                )
            } catch (e: IOException) {
                MarsUiState.Error
            } catch (e: HttpException) {
                MarsUiState.Error
            }
        }
    }
/*
Android フレームワークでは、ViewModel を作成する際にコンストラクタで値を渡すことができません。
この制限を回避するために ViewModelProvider.Factory オブジェクトを実装します。
Factory パターンは、オブジェクトの作成に使用される作成パターンです。
MarsViewModel.Factory オブジェクトはアプリケーション コンテナを使用して marsPhotosRepository を取得します。
次に、ViewModel オブジェクトの作成時にこのリポジトリを ViewModel に渡します。

関数 getMarsPhotos() の下に、コンパニオン オブジェクトのコードを入力します。
コンパニオン オブジェクトは、すべてのユーザーが使用するオブジェクトのインスタンスを 1 つ使用できる点で便利です。
高価なオブジェクトのインスタンスを新たに作成する必要はありません。これは実装の詳細であり、
分離することで、アプリのコードの他の部分に影響を与えずに変更を加えることができます。

APPLICATION_KEY は ViewModelProvider.AndroidViewModelFactory.Companion オブジェクトの一部で、
アプリの MarsPhotosApplication オブジェクトの検出に使用されます。
このオブジェクトには、依存関係インジェクションに使用されるリポジトリを取得するための container プロパティがあります
 */

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as MarsPhotosApplication)
                val marsPhotosRepository = application.container.marsPhotosRepository
                MarsViewModel(marsPhotosRepository = marsPhotosRepository)
            }
        }
    }

}
