package com.tuwaiq.movieapp.notification

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class MovieWorker(private val context: Context, private val params: WorkerParameters) :
    Worker(context, params) {
    override fun doWork(): Result {
        NotificationHelper(context).createNotification(
            inputData.getString("title").toString(),
            inputData.getString("message").toString())
        return Result.success()
    }
}