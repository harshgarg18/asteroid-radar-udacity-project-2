package com.udacity.asteroidradar.main

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.*
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.database.AsteroidRadarDatabase
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.repository.AsteroidRepository
import kotlinx.coroutines.launch
import timber.log.Timber

enum class MenuOptionsFilter { WEEK, TODAY, SAVED }
enum class AsteroidApiStatus { LOADING, DONE }

class MainViewModel(app: Application) : AndroidViewModel(app) {

    private val database = AsteroidRadarDatabase.getInstance(app)
    private val asteroidRepository = AsteroidRepository(database)

    private val asteroidMenuOption = MutableLiveData<MenuOptionsFilter>()

    val asteroids = Transformations.switchMap(asteroidMenuOption) {
        asteroidRepository.getAsteroids(it)
    }
    val asteroidApiStatus = asteroidRepository.asteroidApiStatus
    val pictureOfDay = asteroidRepository.pictureOfDay

    private val _navigateToSelectedAsteroid = MutableLiveData<Asteroid?>()
    val navigateToSelectedAsteroid: LiveData<Asteroid?>
        get() = _navigateToSelectedAsteroid

    init {
        viewModelScope.launch {
            try {
                asteroidRepository.refreshPictureOfDay()
                asteroidRepository.refreshAsteroids()
            } catch (e: Exception) {
                Toast.makeText(
                    app,
                    app.applicationContext.getString(R.string.network_error_toast),
                    Toast.LENGTH_SHORT
                ).show()
                Timber.e(e)
                asteroidApiStatus.value = AsteroidApiStatus.DONE
            }
        }
        asteroidMenuOption.value = MenuOptionsFilter.WEEK
    }

    fun onMenuItemFilterChanged(filter: MenuOptionsFilter) {
        asteroidMenuOption.value = filter
    }

    fun displayAsteroidDetails(asteroid: Asteroid) {
        _navigateToSelectedAsteroid.value = asteroid
    }

    fun displayAsteroidDetailsCompleted() {
        _navigateToSelectedAsteroid.value = null
    }

    /**
     * Factory for constructing MainViewModel with parameter
     */
    class Factory(private val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct ViewModel")
        }
    }
}
