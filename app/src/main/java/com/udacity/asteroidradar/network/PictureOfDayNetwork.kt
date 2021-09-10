package com.udacity.asteroidradar.network

import com.squareup.moshi.Json
import com.udacity.asteroidradar.database.PictureOfDayEntity

data class PictureOfDayNetwork(
    val date: String,
    val title: String,
    val url: String,
    @Json(name = "media_type") val mediaType: String
)

fun PictureOfDayNetwork.asDatabaseModel(): PictureOfDayEntity {
    return PictureOfDayEntity(
        date = this.date,
        title = this.title,
        url = this.url,
        mediaType = this.mediaType
    )
}
