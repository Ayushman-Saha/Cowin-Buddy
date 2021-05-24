package com.ayushman.vaccinenotifier.ui.fragmentDistrictSelect

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.ayushman.vaccinenotifier.database.LocalDatabase
import com.ayushman.vaccinenotifier.database.userData.DatabaseUserData
import com.ayushman.vaccinenotifier.repository.DistrictRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FragmentDistrictSelectViewModel(application: Application, stateId: Int, stateName :String) : AndroidViewModel(application) {


    //Initialising the localDB
    private val database = LocalDatabase.getInstance(application)
    //Initialising the Repositories
    private val districtRepository = DistrictRepository(database, stateId, stateName)

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                districtRepository.refreshDistricts()
            }
        }
    }

    val districts = districtRepository.districts

    fun addToDb(data: DatabaseUserData) {
        viewModelScope.launch {
            addUserDetails(data)
        }
    }

    private suspend fun addUserDetails(data : DatabaseUserData) {
        withContext(Dispatchers.IO) {
            database.userDataDao.insert(data)
        }
    }

    class Factory(val app: Application, stateId: Int, stateName: String) : ViewModelProvider.Factory {
        val id = stateId
        val stName = stateName
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(FragmentDistrictSelectViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return FragmentDistrictSelectViewModel(app,id,stName) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}