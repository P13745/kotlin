package com.example.bookshelf.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable




/*
@Serializable
data class Id(
    val id: String,
)

@Serializable
data class Items (
    val items: List<Id>,
)

@Serializable
data class Thumbnail(
    val thumbnail :String
)

@Serializable
data class BookListResponse(
    val items: ArrayList<Item>
)

@Serializable
data class Item(
    val id: String
)

@Serializable
data class VolumeInfoResponse(
    val volumeInfo: VolumeInfo
)

@Serializable
data class VolumeInfo(
    val imageLinks: ImageLinks?
)

@Serializable
data class ImageLinks(
    val thumbnail: String
)
*/


@Serializable
data class QueryResponse(
    val kind: String,
    val totalItems: Int,
    val items: List<Book>?,

)

//item[~].volumeInfo.imageLinks.thmbnail と(http→https)でリンクを取得


/*
kind
totalItems
Items--------List<Book>

Book----id
        description
        volumeInfo-------title
        saleInfo         subtitle
        |                description
        |                imageLinks-------------smallThumbnail
        |                auther                thumbnail(ココ)
        |                publisher
        |                publishedData
        country
        isEbook
        listPrice-------amount
                        country

 */
@Serializable
data class Book(
    val id: String,
    val description: String,
    val volumeInfo: VolumeInfo,
    val saleInfo: SaleInfo
)

@Serializable
data class VolumeInfo(
    val title: String,
    val subtitle: String,
    val description: String,
    val imageLinks: ImageLinks? = null,
    val authors: List<String>,
    val publisher: String,
    val publishedDate: String,
)

@Serializable
data class ImageLinks(
    val smallThumbnail: String,
    val thumbnail: String,
) {
    val convertToHhttps : String
        get() = thumbnail.replace("http", "https")
}

@Serializable
data class SaleInfo(
    val country: String,
    val isEbook: Boolean,
    val listPrice: ListPrice?
)

@Serializable
data class ListPrice(
    val amount: Float?,
    val currency: String? = ""
)