/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.busschedule.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.busschedule.BusScheduleApplication
import com.example.busschedule.data.BusSchedule
import com.example.busschedule.data.BusScheduleDao
import kotlinx.coroutines.flow.Flow

class BusScheduleViewModel(private val busScheduleDao: BusScheduleDao): ViewModel() {

    // Get example bus schedule
    fun getFullSchedule(): Flow<List<BusSchedule>> = busScheduleDao.getAll()
        /*
        flowOf(
        listOf(
            BusSchedule(
                1,
                "Example Street",
                0
            )
        )
    )
         */
    // Get example bus schedule by stop
    fun getScheduleFor(name: String): Flow<List<BusSchedule>> = busScheduleDao.getByName(name)
        /*
        flowOf(
        listOf(
            BusSchedule(
                1,
                "Example Street",
                0
            )
        )
    )
         */

    companion object {
        val factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val x = this
                val application = (x[APPLICATION_KEY] as BusScheduleApplication)
                BusScheduleViewModel(application.database.busScheduleDao())
            }
        }
    }

/*
    companion object {
        val factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BusScheduleApplication
                BusScheduleViewModel(application.database.busScheduleDao())
            }
        }
    }

     */

/*
    companion object {
        val factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                /*
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BusScheduleApplication)
                BusScheduleViewModel(application.database.busScheduleDao())

                 */
                BusScheduleViewModel(busScheduleApplication().database.busScheduleDao())
            }
        }
    }

 */

    /*
    fun CreationExtras.inventoryApplication(): InventoryApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as InventoryApplication)


    →
    initializer {
            ItemEditViewModel(
                this.createSavedStateHandle(),
                inventoryApplication().container.itemsRepository
            )
        }
     */

        /*
        viewModelFactory ファンクションは、Compose プラグインによって提供されるもので、
        Compose アプリケーション内で ViewModel を作成するためのファクトリを作成します。

        initializer ブロックは、ViewModel が初期化される際に実行されるコードを指定します。
        この中で、application 変数を取得し、それを使用して BusScheduleViewModel を生成しています。
        BusScheduleViewModel のコンストラクタには、database.busScheduleDao() で取得した DAO インスタンスが渡されています。
        factory プロパティは、Compose アプリケーション内で ViewModel を作成するためのファクトリインスタンスです。このファクトリを使って ViewModel を作成することで、ViewModel のライフサイクルが適切に管理されます。


         */


}

//fun CreationExtras.busScheduleApplication(): BusScheduleApplication = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BusScheduleApplication

