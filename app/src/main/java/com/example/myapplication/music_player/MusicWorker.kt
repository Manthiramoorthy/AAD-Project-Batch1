package com.example.myapplication.music_player

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.annotation.UiContext
import androidx.core.content.ContextCompat
import androidx.work.Worker
import androidx.work.WorkerParameters

class MusicWorker(private val context: Context, params: WorkerParameters): Worker(context, params) {
    val LOG_TAG = MusicWorker::class.simpleName
    override fun doWork(): Result {
        Log.d(LOG_TAG, "do work")
        val intent = Intent(context, MusicForegroundService::class.java)
        ContextCompat.startForegroundService(context, intent)
        return Result.success()
    }
}