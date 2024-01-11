package com.example.amphibians.network

import com.example.amphibians.model.AmphibiansData
import retrofit2.http.GET

interface AmphibiansApiService {
    @GET("amphibians")
    //https://android-kotlin-fun-mars-server.appspot.com/amphibians の後ろ/amphibiansのところ
    suspend fun getData(): List<AmphibiansData>
}