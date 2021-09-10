package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AsteroidDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg asteroids: AsteroidEntity)

    @Query("SELECT * FROM weekly_asteroid_table WHERE close_approach_date BETWEEN :startDate AND :endDate ORDER BY close_approach_date")
    fun getAsteroids(startDate: String, endDate: String): LiveData<List<AsteroidEntity>>

    @Query("DELETE FROM weekly_asteroid_table WHERE close_approach_date < :date")
    suspend fun deletePastAsteroids(date: String)
}
