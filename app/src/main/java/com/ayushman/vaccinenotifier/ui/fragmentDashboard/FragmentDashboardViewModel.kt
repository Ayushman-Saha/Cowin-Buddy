package com.ayushman.vaccinenotifier.ui.fragmentDashboard

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.ayushman.vaccinenotifier.database.LocalDatabase
import com.ayushman.vaccinenotifier.repository.ScheduleRepository
import com.ayushman.vaccinenotifier.repository.UserDataRepository
import com.ayushman.vaccinenotifier.work.AlarmReceiver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FragmentDashboardViewModel(applicationData : Application, private val dateList :  List<String>) : AndroidViewModel(applicationData) {


    //Initialising the localDB
    private val database = LocalDatabase.getInstance(applicationData)
    //Initialising the Repositories
    private val scheduleRepository = ScheduleRepository(database)
    private val userDataRepository = UserDataRepository(database)

    private val notifyIntent = Intent(applicationData.applicationContext, AlarmReceiver::class.java)
    private val alarmManager = applicationData.applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    private val REQUEST_CODE = 0
    private val notifyPendingIntent = PendingIntent.getBroadcast(
        applicationData.applicationContext,
        REQUEST_CODE,
        notifyIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val userData = database.userDataDao.get()
                userData?.let {
                    scheduleRepository.refreshSchedule(it.userDistrictCode,dateList)
                }
            }
        }
    }

    val schedules = scheduleRepository.schedulesAbove18
    val userData = userDataRepository.userData

    fun changeLocation() {
        cancelBackgroundNotifications()
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                database.userDataDao.clear()
            }
        }
    }

    fun refreshList() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val userData = database.userDataDao.get()
                userData?.let {
                    scheduleRepository.refreshSchedule(it.userDistrictCode,dateList)
                }
            }
        }
    }

    fun changeNotificationPreferences(preference : Boolean) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val userData = database.userDataDao.get()
                userData!!.isNotificationsEnabled = preference
                database.userDataDao.insert(userData)
            }
        }
        if(preference)
            setupBackgroundNotifications()
        else
            cancelBackgroundNotifications()
    }

    fun setupBackgroundNotifications() {
        val triggerTime = SystemClock.currentThreadTimeMillis()

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            triggerTime,
            AlarmManager.INTERVAL_HALF_HOUR,
            notifyPendingIntent
        )

    }

    fun updateList (size : Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val userData = database.userDataDao.get()
                userData!!.isListEmpty = size <= 0
                database.userDataDao.insert(userData)
            }
        }
    }

    fun cancelBackgroundNotifications () {
        alarmManager.cancel(notifyPendingIntent)
    }

    class Factory(val app: Application, val dateList :  List<String>) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(FragmentDashboardViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return FragmentDashboardViewModel(app,dateList) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

}