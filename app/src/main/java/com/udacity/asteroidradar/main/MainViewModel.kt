package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.*
import com.udacity.asteroidradar.database.AsteroidRadarDatabase
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.repository.AsteroidRepository
import kotlinx.coroutines.launch

enum class MenuOptionsFilter { WEEK, TODAY, SAVED }
enum class AsteroidApiStatus { LOADING, DONE }

class MainViewModel(app: Application) : AndroidViewModel(app) {

    private val database = AsteroidRadarDatabase.getInstance(app)
    private val asteroidRepository = AsteroidRepository(database)

    private val asteroidMenuOption = MutableLiveData<MenuOptionsFilter>()

//    val asteroids = asteroidRepository.asteroids
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
            asteroidRepository.refreshPictureOfDay()
            asteroidRepository.refreshAsteroids()
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
