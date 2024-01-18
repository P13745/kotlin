package com.example.bookshelf.data



import com.example.bookshelf.network.BookshelfApiService

interface BookshelfDataRepository {
    suspend fun getBookshelfData(): List<String>


    //ここを2つにして、検索結果を取得するものと、そこの各々から画像のリンクをとってくるのに
}

class NetworkBookshelfDataRepository(
    private val bookshelfApiService: BookshelfApiService
) : BookshelfDataRepository {
    override suspend fun getBookshelfData(): List<String> {
        try {
            val x = bookshelfApiService.getListData()


            return x.items?.map { it.volumeInfo.imageLinks?.convertToHhttps ?: "" } ?: listOf("")
        } catch (e: Exception) {
            println(e)
            return listOf()
        }
    }
}
