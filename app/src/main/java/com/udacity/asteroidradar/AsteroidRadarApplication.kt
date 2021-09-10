package com.udacity.asteroidradar

import android.app.Application
import android.os.Build
import androidx.work.*
import com.udacity.asteroidradar.work.AsteroidListDeleteWorker
import com.udacity.asteroidradar.work.AsteroidListNetworkWorker
import com.udacity.asteroidradar.work.PictureOfDayDeleteWorker
import com.udacity.asteroidradar.work.PictureOfDayNetworkWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.TimeUnit

class AsteroidRadarApplication : Application() {

    private val applicationScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        delayedInit()
    }

    private fun delayedInit() = applicationScope.launch {
        setUpRecurringWork()
    }

    private fun setUpRecurringWork() {
        val workManager = WorkManager.getInstance()

        val downloadConstraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresBatteryNotLow(true)
            .setRequiresCharging(true)
            .apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    setRequiresDeviceIdle(true)
                }
            }
            .build()

        val asteroidListNetworkRequest =
            PeriodicWorkRequestBuilder<AsteroidListNetworkWorker>(1, TimeUnit.DAYS)
                .setConstraints(downloadConstraints)
                .build()

        val pictureOfDayNetworkRequest =
            PeriodicWorkRequestBuilder<PictureOfDayNetworkWorker>(1, TimeUnit.DAYS)
                .setConstraints(downloadConstraints)
                .build()

        workManager.enqueueUniquePeriodicWork(
            AsteroidListNetworkWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            asteroidListNetworkRequest
        )

        workManager.enqueueUniquePeriodicWork(
            PictureOfDayNetworkWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            pictureOfDayNetworkRequest
        )

        val deleteConstraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .setRequiresCharging(true)
            .build()

        val asteroidListDeleteRequest =
            PeriodicWorkRequestBuilder<AsteroidListDeleteWorker>(1, TimeUnit.DAYS)
                .setConstraints(deleteConstraints)
                .build()

        val pictureOfDayDeleteRequest =
            PeriodicWorkRequestBuilder<PictureOfDayDeleteWorker>(1, TimeUnit.DAYS)
                .setConstraints(deleteConstraints)
                .build()

        workManager.enqueueUniquePeriodicWork(
            AsteroidListDeleteWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            asteroidListDeleteRequest
        )

        workManager.enqueueUniquePeriodicWork(
            PictureOfDayDeleteWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            pictureOfDayDeleteRequest
        )


    }
}
