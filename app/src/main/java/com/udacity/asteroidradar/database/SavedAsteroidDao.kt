package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SavedAsteroidDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(asteroid: SavedAsteroidEntity)

    @Query("SELECT * FROM saved_asteroids")
    fun getAllSavedAsteroids(): LiveData<List<SavedAsteroidEntity>>
}
