package com.udacity.asteroidradar.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.database.AsteroidRadarDatabase
import com.udacity.asteroidradar.repository.AsteroidRepository
import retrofit2.HttpException

class AsteroidListDeleteWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "AsteroidListDeleteWorker"
    }

    override suspend fun doWork(): Result {
        val database = AsteroidRadarDatabase.getInstance(applicationContext)
        val repository = AsteroidRepository(database)

        return try {
            repository.clearPastAsteroids()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }
}
