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

package com.example.waterme.data

import android.content.Context
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.waterme.model.Plant
import com.example.waterme.worker.WaterReminderWorker
import java.util.concurrent.TimeUnit

class WorkManagerWaterRepository(context: Context) : WaterRepository {
    private val workManager = WorkManager.getInstance(context)

    override val plants: List<Plant>
        get() = DataSource.plants

    override fun scheduleReminder(duration: Long, unit: TimeUnit, plantName: String) {
        val data = Data.Builder()
        //Data.Builder を使用して、data という変数を作成します。
        // データは単一の文字列値（WaterReminderWorker.nameKey がキーで、
        // scheduleReminder() に渡される plantName が値）で構成する必要があります。
        data.putString(WaterReminderWorker.nameKey,plantName)

        val workRequestBuilder =
            OneTimeWorkRequestBuilder<WaterReminderWorker>()
                .setInitialDelay(duration, unit)
                .setInputData(data.build())
                .build()
        //WaterReminderWorker クラスを使用して 1 回限りの処理リクエストを作成します。
        // scheduleReminder() 関数に渡された duration と unit を使用して、
        // 作成するデータ変数に入力データを設定します。

        workManager.enqueueUniqueWork(
            plantName + duration,
            ExistingWorkPolicy.REPLACE,
            workRequestBuilder
        )


        //workManager の enqueueUniqueWork() メソッドを呼び出します。
        // 植物名に経過時間を連結して渡し、ExistingWorkPolicy および
        // 処理リクエスト オブジェクトとして REPLACE を使用します。


    }
}
