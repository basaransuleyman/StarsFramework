package com.example.starframework

import android.app.NotificationManager
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.starframework.ui.theme.StarFrameworkTheme
import com.starFramework.feature.presentation.MainScreen
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val workManager by lazy { WorkManager.getInstance(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StarFrameworkTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        scheduleNotificationWorker()
    }

    override fun onResume() {
        super.onResume()
        removeNotification()
    }

    private fun scheduleNotificationWorker() {
        val workRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
            .setInitialDelay(5, TimeUnit.SECONDS)
            .build()
        workManager.enqueueUniqueWork(
            NotificationWorker.WORK_NAME,
            ExistingWorkPolicy.REPLACE,
            workRequest
        )
    }

    private fun removeNotification() {
        workManager.cancelUniqueWork(NotificationWorker.WORK_NAME)
        (getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager)?.cancelAll()
    }
}

