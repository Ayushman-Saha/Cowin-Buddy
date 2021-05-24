package com.ayushman.vaccinenotifier.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.ayushman.vaccinenotifier.database.LocalDatabase
import com.ayushman.vaccinenotifier.database.districts.asDomainModel
import com.ayushman.vaccinenotifier.domain.District
import com.ayushman.vaccinenotifier.network.AdminApi
import com.ayushman.vaccinenotifier.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class DistrictRepository(private val localDatabase: LocalDatabase, stateId : Int, stateName : String) {

    val districts : LiveData<List<District>> = Transformations.map(localDatabase.districtDao.get(stateName)) {
        it.asDomainModel()
    }

    val id = stateId
    private val stName = stateName

    suspend fun refreshDistricts() {
        withContext(Dispatchers.IO) {
            try {
                val districts = AdminApi.adminService.getDistrictListAsync(id).await()
                localDatabase.districtDao.insertAll(*districts.asDatabaseModel(stName))
            } catch (e : Throwable) {}
        }
    }
}