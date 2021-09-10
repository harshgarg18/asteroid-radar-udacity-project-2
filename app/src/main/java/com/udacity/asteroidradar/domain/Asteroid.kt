package com.udacity.asteroidradar.domain

import android.os.Parcelable
import com.udacity.asteroidradar.database.AsteroidEntity
import com.udacity.asteroidradar.database.SavedAsteroidEntity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Asteroid(
    val id: Long, val codename: String,
    val closeApproachDate: String,
    val absoluteMagnitude: Double,
    val estimatedDiameter: Double,
    val relativeVelocity: Double,
    val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean
) : Parcelable

fun List<Asteroid>.asDatabaseModel(): Array<AsteroidEntity> {
    return map {
        AsteroidEntity(
            id = it.id,
            codename = it.codename,
            closeApproachDate = it.closeApproachDate,
            absoluteMagnitude = it.absoluteMagnitude,
            estimatedDiameter = it.absoluteMagnitude,
            relativeVelocity = it.relativeVelocity,
            distanceFromEarth = it.distanceFromEarth,
            isPotentiallyHazardous = it.isPotentiallyHazardous
        )
    }.toTypedArray()
}

fun Asteroid.asSavedAsteroidDatabaseModel(): SavedAsteroidEntity {
    return SavedAsteroidEntity(
        id = this.id,
        codename = this.codename,
        closeApproachDate = this.closeApproachDate,
        absoluteMagnitude = this.absoluteMagnitude,
        estimatedDiameter = this.absoluteMagnitude,
        relativeVelocity = this.relativeVelocity,
        distanceFromEarth = this.distanceFromEarth,
        isPotentiallyHazardous = this.isPotentiallyHazardous
    )
}
