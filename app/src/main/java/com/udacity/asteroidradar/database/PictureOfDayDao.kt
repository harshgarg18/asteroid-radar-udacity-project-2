package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PictureOfDayDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPictureOfDay(pictureOfDay: PictureOfDayEntity)

    @Query("SELECT * FROM picture_of_day WHERE date = :date")
    fun getPictureOfDay(date: String): LiveData<PictureOfDayEntity>

    @Query("DELETE FROM picture_of_day WHERE date != :date")
    suspend fun deletePastPictures(date: String)
}
