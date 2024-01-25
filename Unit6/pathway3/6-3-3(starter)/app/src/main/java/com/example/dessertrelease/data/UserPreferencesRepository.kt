package com.example.dessertrelease.data

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException


class UserPreferencesRepository(
    private val dataStore: DataStore<Preferences> //これはkey-valueのペアを格納する
){
    private companion object {
        val IS_LINEAR_LAYOUT = booleanPreferencesKey("is_linear_layout") //SQLと同じ"_"で名前を
        const val TAG = "UserPreferencesRepo"//エラーが起こったとき用に
    }
    suspend fun saveLayoutPreference(isLinearLayout: Boolean) { //書き込み
        dataStore.edit { preferences ->
            preferences[IS_LINEAR_LAYOUT] = isLinearLayout
        }
    }
    /*
    注: この関数が呼び出されて値が設定されるまで、値は DataStore 内に存在しません。
    edit() メソッドで Key-Value ペアを設定すると、値が定義され、
    アプリのキャッシュまたはデータがクリアされるまで初期化されます。
     */

    //読み込み、。
    //DataStore のデータが更新されるたびに、新しい Preferences オブジェクトが Flow に出力されます。
    val isLinearLayout: Flow<Boolean> = dataStore.data
        .catch {
            if (it is IOException) {
                Log.e(TAG, "Error reading preferences.", it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
        preferences[IS_LINEAR_LAYOUT] ?: true //デフォルト指定して
    }
}