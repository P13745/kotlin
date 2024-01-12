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
*/

@Serializable
data class BookListResponse(
    val items: List<Item>
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