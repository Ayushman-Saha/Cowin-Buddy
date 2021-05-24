package com.ayushman.vaccinenotifier.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.ayushman.vaccinenotifier.database.LocalDatabase
import com.ayushman.vaccinenotifier.database.states.asDomainModel
import com.ayushman.vaccinenotifier.domain.State
import com.ayushman.vaccinenotifier.network.AdminApi
import com.ayushman.vaccinenotifier.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class StatesRepository (private val localDatabase: LocalDatabase) {

    val state : LiveData<List<State>> = Transformations.map(localDatabase.statesDao.get()) {
        it.asDomainModel()
    }

    suspend fun refreshStates() {
        withContext(Dispatchers.IO) {
            try {
                val states = AdminApi.adminService.getStateListAsync().await()
                localDatabase.statesDao.insertAll(*states.asDatabaseModel())
            } catch (e : Throwable) {}
        }
    }
}