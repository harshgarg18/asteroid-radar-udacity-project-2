package com.udacity.asteroidradar.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.database.AsteroidRadarDatabase
import com.udacity.asteroidradar.repository.AsteroidRepository
import retrofit2.HttpException

class PictureOfDayNetworkWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "PictureOfDayNetworkWorker"
    }

    override suspend fun doWork(): Result {
        val database = AsteroidRadarDatabase.getInstance(applicationContext)
        val repository = AsteroidRepository(database)

        return try {
            repository.refreshPictureOfDay()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }
}
