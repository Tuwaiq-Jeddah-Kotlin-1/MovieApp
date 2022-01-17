package com.tuwaiq.movieapp.notification

import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.tuwaiq.movieapp.MainActivity
import java.util.concurrent.TimeUnit

class MovieNotificationRepo {
    private val notification = " Did You Check the New Movies ? "
    fun myNotification(mainActivity: MainActivity) {
        val myWorkRequest = PeriodicWorkRequest
            .Builder(MovieWorker::class.java, 24, TimeUnit.HOURS)
            .setInputData(workDataOf(
                "title" to "Movie App",
                "message" to notification)
            )
            .build()
        WorkManager.getInstance(mainActivity)
            .enqueueUniquePeriodicWork("periodicStockWorker",
                ExistingPeriodicWorkPolicy.REPLACE,
                myWorkRequest)
    }
}