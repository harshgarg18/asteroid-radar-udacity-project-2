package com.udacity.asteroidradar.detail

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.*
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.database.AsteroidRadarDatabase
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.repository.AsteroidRepository
import kotlinx.coroutines.launch

class DetailViewModel(private val app: Application, private val asteroid: Asteroid) :
    AndroidViewModel(app) {

    private val database = AsteroidRadarDatabase.getInstance(app)
    private val asteroidRepository = AsteroidRepository(database)

    private val _navigateToMainFragment = MutableLiveData<Boolean?>()
    val navigateToMainFragment: LiveData<Boolean?>
        get() = _navigateToMainFragment

    private val _displayAuExplanation = MutableLiveData<Boolean?>()
    val displayAuExplanation: LiveData<Boolean?>
        get() = _displayAuExplanation

    fun onHelpButtonClicked() {
        _displayAuExplanation.value = true
    }

    fun doneDisplayingHelp() {
        _displayAuExplanation.value = null
    }

    fun onSaveAsteroid() {
        viewModelScope.launch {
            asteroidRepository.saveAsteroid(asteroid)
            Toast.makeText(
                app.applicationContext,
                app.applicationContext.getString(R.string.asteroid_saved),
                Toast.LENGTH_SHORT
            ).show()
            _navigateToMainFragment.value = true
        }
    }

    fun doneNavigating() {
        _navigateToMainFragment.value = null
    }

    /**
     * Factory for constructing DetailViewModel with parameters
     */
    class Factory(private val app: Application, private var asteroid: Asteroid) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return DetailViewModel(app, asteroid) as T
            }
            throw IllegalArgumentException("Unable to construct ViewModel")
        }
    }

}
