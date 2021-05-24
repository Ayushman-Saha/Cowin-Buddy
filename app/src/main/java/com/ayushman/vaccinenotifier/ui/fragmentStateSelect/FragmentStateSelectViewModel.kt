package com.ayushman.vaccinenotifier.ui.fragmentStateSelect

import android.app.Application
import androidx.lifecycle.*
import com.ayushman.vaccinenotifier.database.LocalDatabase
import com.ayushman.vaccinenotifier.domain.State
import com.ayushman.vaccinenotifier.repository.StatesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FragmentStateSelectViewModel(application: Application) : AndroidViewModel(application) {

    //Initialising the localDB
    private val database = LocalDatabase.getInstance(application)
    //Initialising the Repositories
    private val statesRepository = StatesRepository(database)

    //Encapsulated LiveData to see if playlist is clicked
    private val _navigateToDistrictList = MutableLiveData<State?>()
    val navigateToDistrictList : MutableLiveData<State?>
        get() = _navigateToDistrictList

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                statesRepository.refreshStates()
            }
        }
    }

    val states = statesRepository.state

    //Functions to modify values of the liveData
    fun onPlaylistClicked(state : State) {
        _navigateToDistrictList.value = state
    }

    fun onPlaylistTrackNavigated() {
        _navigateToDistrictList.value = null
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(FragmentStateSelectViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return FragmentStateSelectViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

}