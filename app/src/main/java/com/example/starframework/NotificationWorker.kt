package com.example.starframework

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class NotificationWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    companion object {
        const val WORK_NAME = "notification_work"
    }

    @RequiresApi(Build.VERSION_CODES.HONEYCOMB)
    override suspend fun doWork(): Result {
        Log.d("NotificationWorker", "Worker started.")
        showNotification(5)
        Log.d("NotificationWorker", "Notification is sent.")
        return Result.success()
    }

    @RequiresApi(Build.VERSION_CODES.HONEYCOMB)
    fun showNotification(starCount: Int) {
        val notificationManager =
            applicationContext.applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "star_channel",
                "Star Updates",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }
        val intent = Intent(applicationContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(applicationContext, 0, intent, 0)

        val notification = NotificationCompat.Builder(applicationContext, "star_channel")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Star Count")
            .setContentText("There are $starCount stars.")
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(1, notification)
    }
}