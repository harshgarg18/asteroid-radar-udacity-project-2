package com.udacity.asteroidradar.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PictureOfDay(
    val title: String,
    val url: String,
    val mediaType: String
) : Parcelable
