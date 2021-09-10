package com.udacity.asteroidradar.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [AsteroidEntity::class, SavedAsteroidEntity::class, PictureOfDayEntity::class],
    version = 1
)
abstract class AsteroidRadarDatabase : RoomDatabase() {

    abstract val asteroidDao: AsteroidDao
    abstract val savedAsteroidDao: SavedAsteroidDao
    abstract val pictureOfDayDao: PictureOfDayDao

    companion object {
        @Volatile
        private var INSTANCE: AsteroidRadarDatabase? = null

        fun getInstance(context: Context): AsteroidRadarDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AsteroidRadarDatabase::class.java,
                        "asteroid_radar_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}
