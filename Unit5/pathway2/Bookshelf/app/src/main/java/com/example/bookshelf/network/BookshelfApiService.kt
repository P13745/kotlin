package com.example.bookshelf.network






import com.example.bookshelf.model.QueryResponse
import retrofit2.http.GET
import retrofit2.http.Query


/*=================================================================================*/
const val APIKEY: String =""
/*=================================================================================*/

interface BookshelfApiService {
    //@GET("volumes?q=classical+music+history+soviet&key=$APIKEY")
    @GET("volumes")
    suspend fun getListData(@Query("q") query: String = "classical+music+history+soviet", @Query("key") apiKey: String = APIKEY): QueryResponse


/*

    //@GET("volumes/{id}?key=$APIKEY")
    @GET("volumes/{id}")
    suspend fun getThumbnail(@Path("id") id: String, @Query("key") apiKey: String = APIKEY): VolumeInfoResponse
//逆にここ上二つしかない

    suspend fun convertBookIdListToThumbnailsList(bookId: List<String>): List<String> {
        try {
            return bookId.map { getThumbnail(it).volumeInfo.imageLinks?.thumbnail.orEmpty() }
            //return listOf("T7IwDwAAQBAJ")
        } finally {
            // 任意の後処理があればここに追加
        }
    }

    @GET("volumes?q=classical+music+history+soviet&key=$APIKEY")
    suspend fun getThumbnailsList(): List<String> {
        val bookIds = getListData().items.map { it.id }
        return convertBookIdListToThumbnailsList(bookIds)
        //return listOf("http://books.google.com/books/publisher/content?id=T7IwDwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&imgtk=AFLRE70NhluwryqirEAJIWm_adh1aZvE2JXHvVIv8Ka97PbVw0n44Zy6tqHG_A-64QRwoJYQF0MTx7ARMz4vwZPEq3TvTmfNMA1TG2axEPijfYYXVFZvsTRfQL5nov4aPBHoBhEpCyz3&source=gbs_api")
    }



/*
    suspend fun convertBookIdListToThumbnailsList(bookId: List<Id>): List<Thumbnail> {
        try {
            return bookId.map { getThumbnail(it.toString()) }
        } finally {
        }

    }

    @GET("volumes?q=classical+music+history+soviet&key=$APIKEY")
    suspend fun getThumbnailsList() : List<Thumbnail> {
        return convertBookIdListToThumbnailsList(getListData().items)
    }

 */

*/


}
