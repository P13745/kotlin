package com.example.amphibians.data

import com.example.amphibians.model.AmphibiansData
import com.example.amphibians.network.AmphibiansApiService

interface AmphibiansDataRepository {
    suspend fun getAmphibiansData(): List<AmphibiansData>
}

class NetworkAmphibiansDataRepository(
    private val amphibiansApiService: AmphibiansApiService
) : AmphibiansDataRepository {
    override suspend fun getAmphibiansData(): List<AmphibiansData> = amphibiansApiService.getData()
}