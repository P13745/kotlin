package com.example.a30daysofwellnes.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.a30daysofwellnes.R

data class Music(
    @StringRes val nameRes: Int,
    val year: String,
    @StringRes val descriptionRes: Int,
     val composer: String,
    @DrawableRes val imageRes: Int
)



    val musicList = listOf(
        Music(
            nameRes = R.string.name8,
            year= "1874",
            descriptionRes=R.string.description8,
            composer = "Mussorgsky",
            imageRes=R.drawable.score8
        ),
        Music(
            nameRes = R.string.name12,
            year= "1889-1897",
            descriptionRes=R.string.description12,
            composer = "Satie",
            imageRes=R.drawable.score12
        ),
        Music(
            nameRes = R.string.name22,
            year= "1901",
            descriptionRes=R.string.description22,
            composer = "Ravel",
            imageRes=R.drawable.score22
        ),
        Music(
            nameRes = R.string.name7,
            year= "1903",
            descriptionRes=R.string.description7,
            composer = "Scriabin",
            imageRes=R.drawable.score7
        ),
        Music(
            nameRes = R.string.name19,
            year= "1904-1905",
            descriptionRes=R.string.description19,
            composer = "Ravel",
            imageRes=R.drawable.score19
        ),
        Music(
            nameRes = R.string.name15,
            year= "1904-1907",
            descriptionRes=R.string.description15,
            composer = "Medtner",
            imageRes=R.drawable.score15
        ),
        Music(
            nameRes = R.string.name9,
            year= "1905-1908",
            descriptionRes=R.string.description9,
            composer = "Albéniz",
            imageRes=R.drawable.score9
        ),
        Music(
            nameRes = R.string.name10,
            year= "1907",
            descriptionRes=R.string.description10,
            composer = "Schoenberg",
            imageRes=R.drawable.score10
        ),
        Music(
            nameRes = R.string.name20,
            year= "1908",
            descriptionRes=R.string.description20,
            composer = "Ravel",
            imageRes=R.drawable.score20
        ),
        Music(
            nameRes = R.string.name14,
            year= "1910-1911",
            descriptionRes=R.string.description14,
            composer = "Medtner",
            imageRes=R.drawable.score14
        ),
        Music(
            nameRes = R.string.name23,
            year= "1910-1920",
            descriptionRes=R.string.description23,
            composer = "Ives",
            imageRes=R.drawable.score23
        ),
        Music(
            nameRes = R.string.name6,
            year= "1914",
            descriptionRes=R.string.description6,
            composer = "Scriabin",
            imageRes=R.drawable.score6
        ),
        Music(
            nameRes = R.string.name1,
            year= "1915-1916",
            descriptionRes=R.string.description1,
            composer = "Szymanowski",
            imageRes=R.drawable.score1
        ),
        Music(
            nameRes = R.string.name3,
            year= "1916-1917",
            descriptionRes=R.string.description3,
            composer = "Feinberg",
            imageRes=R.drawable.score3
        ),
        Music(
            nameRes = R.string.name2,
            year= "1917",
            descriptionRes=R.string.description2,
            composer = "Szymanowski",
            imageRes=R.drawable.score2
        ),
        Music(
            nameRes = R.string.name13,
            year= "1917",
            descriptionRes=R.string.description13,
            composer = "Satie",
            imageRes=R.drawable.score13
        ),
        Music(
            nameRes = R.string.name4,
            year= "1918",
            descriptionRes=R.string.description4,
            composer = "Feinberg",
            imageRes=R.drawable.score4
        ),
        Music(
            nameRes = R.string.name21,
            year= "1919-1920",
            descriptionRes=R.string.description21,
            composer = "Ravel",
            imageRes=R.drawable.score21
        ),
        Music(
            nameRes = R.string.name24,
            year= "1923-1924",
            descriptionRes=R.string.description24,
            composer = "Ives",
            imageRes=R.drawable.score24
        ),
        Music(
            nameRes = R.string.name27,
            year= "1926",
            descriptionRes=R.string.description27,
            composer = "Bartók",
            imageRes=R.drawable.score27
        ),
        Music(
            nameRes = R.string.name25,
            year= "1926",
            descriptionRes=R.string.description25,
            composer = "Hindemith",
            imageRes=R.drawable.score25
        ),
        Music(
            nameRes = R.string.name26,
            year= "1928",
            descriptionRes=R.string.description26,
            composer = "Bartók",
            imageRes=R.drawable.score26
        ),
        Music(
            nameRes = R.string.name17,
            year= "1932",
            descriptionRes=R.string.description17,
            composer = "Poulenc",
            imageRes=R.drawable.score17
        ),
        Music(
            nameRes = R.string.name28,
            year= "1936",
            descriptionRes=R.string.description28,
            composer = "Bartók",
            imageRes=R.drawable.score28
        ),
        Music(
            nameRes = R.string.name30,
            year= "1936",
            descriptionRes=R.string.description30,
            composer = "Eisler",
            imageRes=R.drawable.score30
        ),
        Music(
            nameRes = R.string.name29,
            year= "1937",
            descriptionRes=R.string.description29,
            composer = "Bartók",
            imageRes=R.drawable.score29
        ),
        Music(
            nameRes = R.string.name5,
            year= "1938",
            descriptionRes=R.string.description5,
            composer = "Feinberg",
            imageRes=R.drawable.score5
        ),
        Music(
            nameRes = R.string.name11,
            year= "1942",
            descriptionRes=R.string.description11,
            composer = "Schoenberg",
            imageRes=R.drawable.score11
        ),
        Music(
            nameRes = R.string.name18,
            year= "1943",
            descriptionRes=R.string.description18,
            composer = "Poulenc",
            imageRes=R.drawable.score18
        ),
        Music(
            nameRes = R.string.name16,
            year= "1949",
            descriptionRes=R.string.description16,
            composer = "Poulenc",
            imageRes=R.drawable.score16
        ),


    )
