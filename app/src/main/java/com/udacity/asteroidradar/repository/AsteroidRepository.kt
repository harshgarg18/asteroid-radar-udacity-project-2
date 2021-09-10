package com.udacity.asteroidradar.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.database.AsteroidRadarDatabase
import com.udacity.asteroidradar.database.asDomainModel
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.domain.PictureOfDay
import com.udacity.asteroidradar.domain.asDatabaseModel
import com.udacity.asteroidradar.domain.asSavedAsteroidDatabaseModel
import com.udacity.asteroidradar.main.AsteroidApiStatus
import com.udacity.asteroidradar.main.MenuOptionsFilter
import com.udacity.asteroidradar.network.NasaApi
import com.udacity.asteroidradar.network.asDatabaseModel
import com.udacity.asteroidradar.network.parseAsteroidsJsonResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class AsteroidRepository(private val database: AsteroidRadarDatabase) {

//    val asteroids: LiveData<List<Asteroid>> =
//        Transformations.map(
//            database.asteroidDao.getAsteroids(
//                getCurrentDate(),
//                getCurrentDate(7)
//            )
//        ) {
//            it?.asDomainModel()
//        }

    fun getAsteroids(filter: MenuOptionsFilter): LiveData<List<Asteroid>> {
        return when (filter) {
            MenuOptionsFilter.WEEK ->
                Transformations.map(
                    database.asteroidDao.getAsteroids(
                        getCurrentDate(),
                        getCurrentDate(7)
                    )
                ) {
                    it?.asDomainModel()
                }
            MenuOptionsFilter.TODAY ->
                Transformations.map(
                    database.asteroidDao.getAsteroids(
                        getCurrentDate(),
                        getCurrentDate()
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
        Transformations.map(database.pictureOfDayDao.getPictureOfDay(getCurrentDate())) {
            it?.asDomainModel()
        }

    suspend fun refreshAsteroids() {
        asteroidApiStatus.value = AsteroidApiStatus.LOADING
        withContext(Dispatchers.IO) {
            val asteroidsString = NasaApi.asteroidListRetrofitService.getAsteroidList(
                getCurrentDate(),
                getCurrentDate(7)
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
            database.asteroidDao.deletePastAsteroids(getCurrentDate())
        }
    }

    suspend fun clearPastPicturesOfDay() {
        withContext(Dispatchers.IO) {
            database.pictureOfDayDao.deletePastPictures(getCurrentDate())
        }
    }

    private fun getCurrentDate(plus: Int = 0): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, plus)
        val sdf = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
        // NASA APIs are using UTC TimeZone
        // data fails with JSONException for IST (my local) zone around 12:30 am
        sdf.timeZone = TimeZone.getTimeZone("UTC")
        return sdf.format(calendar.time)
    }
}
