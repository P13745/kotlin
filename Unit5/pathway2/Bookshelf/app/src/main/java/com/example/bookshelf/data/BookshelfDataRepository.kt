package com.example.bookshelf.data



import com.example.bookshelf.network.BookshelfApiService

interface BookshelfDataRepository {
    suspend fun getBookshelfData(): List<String>
}

class NetworkBookshelfDataRepository(
    private val bookshelfApiService: BookshelfApiService
) : BookshelfDataRepository {
    override suspend fun getBookshelfData(): List<String> = bookshelfApiService.getThumbnailsList()
}