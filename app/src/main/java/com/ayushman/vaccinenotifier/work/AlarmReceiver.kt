package com.ayushman.vaccinenotifier.work

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.ayushman.vaccinenotifier.database.LocalDatabase
import com.ayushman.vaccinenotifier.repository.ScheduleRepository
import com.ayushman.vaccinenotifier.utils.generateDates
import com.ayushman.vaccinenotifier.utils.sendNotification
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AlarmReceiver : BroadcastReceiver() {

    private val applicationScope = CoroutineScope(Dispatchers.Default)


    override fun onReceive(context: Context?, intent: Intent?) {
            applicationScope.launch {
                work(context!!)
        }
    }

    private suspend fun work(appContext : Context) {
        val database = LocalDatabase.getInstance(appContext)
        val repository = ScheduleRepository(database)

        try {
            val userData = database.userDataDao.get()

            userData?.let {
                if (it.isNotificationsEnabled) {
                    repository.refreshSchedule(it.userDistrictCode, generateDates())
                    val scheduleList = database.scheduleDao.getSingleList()
                    if (scheduleList.isNotEmpty()) {
                        val notificationManager = ContextCompat.getSystemService(
                            appContext,
                            NotificationManager::class.java
                        ) as NotificationManager
                        notificationManager.sendNotification(appContext)
                    }
                }
            }
        } catch (e : Exception) { }
    }
}