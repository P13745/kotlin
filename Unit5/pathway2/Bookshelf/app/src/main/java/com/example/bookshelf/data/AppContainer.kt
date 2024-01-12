package com.example.bookshelf.data

import com.example.bookshelf.network.BookshelfApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val BookshelfDataRepository: BookshelfDataRepository
}


class DefaultAppContainer : AppContainer {
    private val baseUrl = "https://www.googleapis.com/books/v1/"
    //"https://www.googleapis.com/books/v1/の後にvolumes"がくる


    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json/* { ignoreUnknownKeys = true }*/.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val retrofitService: BookshelfApiService by lazy {
        retrofit.create(BookshelfApiService::class.java)
    }


    override val BookshelfDataRepository: BookshelfDataRepository by lazy {
        NetworkBookshelfDataRepository(retrofitService)
    }
}