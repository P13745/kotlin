package com.example.mycity.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.mycity.R


enum class Category {
    Nature,City,Landmark
}

data class Location (
    @StringRes val nameId: Int,
    @StringRes val shortDescriptionId: Int,
    @StringRes val detailId: Int,
    val category: Category,
    @DrawableRes val photoId: Int
)

object LocationData {
    val locationList = listOf(
        Location(
            nameId = R.string.name1,
            shortDescriptionId = R.string.description1,
            detailId = R.string.detail1,
            category = Category.Nature,
            photoId = R.drawable.photo1,
        ),
        Location(
            nameId = R.string.name2,
            shortDescriptionId = R.string.description2,
            detailId = R.string.detail2,
            category = Category.City,
            photoId = R.drawable.photo2,
        ),
        Location(
            nameId = R.string.name3,
            shortDescriptionId = R.string.description3,
            detailId = R.string.detail3,
            category = Category.City,
            photoId = R.drawable.photo3,
        ),
        Location(
            nameId = R.string.name4,
            shortDescriptionId = R.string.description4,
            detailId = R.string.detail4,
            category = Category.Landmark,
            photoId = R.drawable.photo4,
        ),
        Location(
            nameId = R.string.name5,
            shortDescriptionId = R.string.description5,
            detailId = R.string.detail5,
            category = Category.Nature,
            photoId = R.drawable.photo5,
        ),
        Location(
            nameId = R.string.name6,
            shortDescriptionId = R.string.description6,
            detailId = R.string.detail6,
            category = Category.Nature,
            photoId = R.drawable.photo6,
        ),
        Location(
            nameId = R.string.name7,
            shortDescriptionId = R.string.description7,
            detailId = R.string.detail7,
            category = Category.Landmark,
            photoId = R.drawable.photo7,
        ),
        Location(
            nameId = R.string.name8,
            shortDescriptionId = R.string.description8,
            detailId = R.string.detail8,
            category = Category.City,
            photoId = R.drawable.photo8,
        ),
        Location(
            nameId = R.string.name9,
            shortDescriptionId = R.string.description9,
            detailId = R.string.detail9,
            category = Category.Landmark,
            photoId = R.drawable.photo9,
        ),



    )
}