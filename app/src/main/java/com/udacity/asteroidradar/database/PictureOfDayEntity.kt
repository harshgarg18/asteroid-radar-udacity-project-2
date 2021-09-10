package com.udacity.asteroidradar.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.udacity.asteroidradar.domain.PictureOfDay

@Entity(tableName = "picture_of_day")
data class PictureOfDayEntity(
    @PrimaryKey
    val date: String,

    val title: String,

    @ColumnInfo(name = "media_type")
    val mediaType: String,

    val url: String
)

fun PictureOfDayEntity.asDomainModel(): PictureOfDay {
    return PictureOfDay(
        title = this.title,
        url = this.url,
        mediaType = this.mediaType
    )
}
