package com.tuwaiq.movieapp.notification

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.work.Worker
import androidx.work.WorkerParameters

class MovieWorker(private val context: Context, params: WorkerParameters) :
    Worker(context, params) {
    @RequiresApi(Build.VERSION_CODES.S)
    override fun doWork(): Result {
        NotificationHelper(context).createNotification(
            inputData.getString("title").toString(),
            inputData.getString("message").toString())
        return Result.success()
    }
}