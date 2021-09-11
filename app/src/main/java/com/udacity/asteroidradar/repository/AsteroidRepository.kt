package com.udacity.asteroidradar.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.database.AsteroidRadarDatabase
import com.udacity.asteroidradar.database.asDomainModel
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.domain.PictureOfDay
import com.udacity.asteroidradar.domain.asDatabaseModel
import com.udacity.asteroidradar.domain.asSavedAsteroidDatabaseModel
import com.udacity.asteroidradar.formatWithDefaultTimeZone
import com.udacity.asteroidradar.formatWithNasaTimeZone
import com.udacity.asteroidradar.getCurrentDate
import com.udacity.asteroidradar.main.AsteroidApiStatus
import com.udacity.asteroidradar.main.MenuOptionsFilter
import com.udacity.asteroidradar.network.NasaApi
import com.udacity.asteroidradar.network.asDatabaseModel
import com.udacity.asteroidradar.network.parseAsteroidsJsonResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class AsteroidRepository(private val database: AsteroidRadarDatabase) {

    fun getAsteroids(filter: MenuOptionsFilter): LiveData<List<Asteroid>> {
        return when (filter) {
            MenuOptionsFilter.WEEK ->
                Transformations.map(
                    database.asteroidDao.getAsteroids(
                        getCurrentDate().formatWithDefaultTimeZone,
                        getCurrentDate(7).formatWithDefaultTimeZone
                    )
                ) {
                    it?.asDomainModel()
                }
            MenuOptionsFilter.TODAY ->
                Transformations.map(
                    database.asteroidDao.getAsteroids(
                        getCurrentDate().formatWithDefaultTimeZone,
                        getCurrentDate().formatWithDefaultTimeZone
                    )
                ) {
                    it?.asDomainModel()
                }
            MenuOptionsFilter.SAVED ->
                Transformations.map(database.savedAsteroidDao.getAllSavedAsteroids()) {
                    it?.asDomainModel()
                }
        }
    }

    val asteroidApiStatus = MutableLiveData(AsteroidApiStatus.LOADING)

    val pictureOfDay: LiveData<PictureOfDay> =
        Transformations.map(
            database.pictureOfDayDao.getPictureOfDay(getCurrentDate().formatWithNasaTimeZone)
        ) {
            it?.asDomainModel()
        }

    suspend fun refreshAsteroids() {
        asteroidApiStatus.value = AsteroidApiStatus.LOADING
        withContext(Dispatchers.IO) {
            val asteroidsString = NasaApi.asteroidListRetrofitService.getAsteroidList(
                getCurrentDate().formatWithDefaultTimeZone,
                getCurrentDate(7).formatWithDefaultTimeZone
            )
            val asteroidList = parseAsteroidsJsonResult(JSONObject(asteroidsString))
            database.asteroidDao.insertAll(*asteroidList.asDatabaseModel())
        }
        asteroidApiStatus.value = AsteroidApiStatus.DONE
    }

    suspend fun refreshPictureOfDay() {
        withContext(Dispatchers.IO) {
            val pictureOfDay = NasaApi.pictureOfDayRetrofitService.getPictureOfDay()
            database.pictureOfDayDao.insertPictureOfDay(pictureOfDay.asDatabaseModel())
        }
    }

    suspend fun saveAsteroid(asteroid: Asteroid) {
        withContext(Dispatchers.IO) {
            database.savedAsteroidDao.insert(asteroid.asSavedAsteroidDatabaseModel())
        }
    }

    suspend fun clearPastAsteroids() {
        withContext(Dispatchers.IO) {
            database.asteroidDao.deletePastAsteroids(getCurrentDate().formatWithDefaultTimeZone)
        }
    }

    suspend fun clearPastPicturesOfDay() {
        withContext(Dispatchers.IO) {
            database.pictureOfDayDao.deletePastPictures(getCurrentDate().formatWithNasaTimeZone)
        }
    }
}
