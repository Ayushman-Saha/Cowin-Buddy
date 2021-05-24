package com.ayushman.vaccinenotifier.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.ayushman.vaccinenotifier.database.LocalDatabase
import com.ayushman.vaccinenotifier.database.schedule.asDomainModel
import com.ayushman.vaccinenotifier.domain.Schedule
import com.ayushman.vaccinenotifier.network.CowinApi
import com.ayushman.vaccinenotifier.network.NetworkSchedule
import com.ayushman.vaccinenotifier.network.NetworkScheduleContainer
import com.ayushman.vaccinenotifier.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class ScheduleRepository (private val database: LocalDatabase){

    val schedulesAbove18 : LiveData<List<Schedule>> = Transformations.map(database.scheduleDao.get()) {
        it.asDomainModel()
    }

    suspend fun refreshSchedule(districtId : Int, date : List<String>) {
        withContext(Dispatchers.IO) {
            database.scheduleDao.clear()
            try {
                val userData = database.userDataDao.get()
                userData!!.isError = false
                database.userDataDao.insert(userData)
                date.forEach { date ->
                    val schedules = CowinApi.cowinService.getScheduleListAsync(districtId, date).await()
                    database.scheduleDao.insertAll(*schedules.asDatabaseModel())
                }
            } catch (e : Throwable) {
                val userData = database.userDataDao.get()
                userData!!.isError = true
                database.userDataDao.insert(userData)
            }
        }
    }
}