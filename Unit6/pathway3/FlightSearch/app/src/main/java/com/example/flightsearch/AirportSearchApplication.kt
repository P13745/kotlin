package com.example.flightsearch

import android.app.Application

class AirportSearchApplication: Application() {
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
}