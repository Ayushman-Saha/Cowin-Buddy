package com.ayushman.vaccinenotifier.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.ayushman.vaccinenotifier.MainActivity
import com.ayushman.vaccinenotifier.R
import com.ayushman.vaccinenotifier.SplashActivity

fun NotificationManager.sendNotification(applicationContext: Context) {

    val NOTIFICATION_ID = 69

    val openIntent = Intent(applicationContext, SplashActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }

    val pendingIntent: PendingIntent =
        PendingIntent.getActivity(applicationContext, 0, openIntent, 0)

    val builder: NotificationCompat.Builder = NotificationCompat.Builder(
        applicationContext,
        applicationContext.getString(R.string.channel_id)
    )
        .setSmallIcon(R.drawable.ic_baseline_medical_services_24)
        .setContentTitle("Vaccine slots available")
        .setContentText("COVID-19 vaccination slots are available for booking in your area. Proceed to cowin dashboard or Aarogya Setu app for booking")
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setStyle(
            NotificationCompat.BigTextStyle()
                .bigText("COVID-19 vaccination slots are available for booking in your area. Proceed to cowin dashboard or Aarogya Setu app for booking")
        )
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)

    notify(NOTIFICATION_ID, builder.build())
}
