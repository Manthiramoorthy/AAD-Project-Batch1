package com.example.myapplication.music_player

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.icu.util.Calendar
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMusicPlayerBinding
import java.util.concurrent.TimeUnit

class MusicPlayerActivity : AppCompatActivity() {
    private val LOG_TAG = MusicPlayerActivity::class.java.simpleName
    lateinit var serviceIntent: Intent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityMusicPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
//      val intent =  Intent(this, MusicService::class.java)
        serviceIntent = Intent(this, MusicForegroundService::class.java)
        binding.startButton.setOnClickListener {
//            startService(intent)
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                ContextCompat.startForegroundService(this, serviceIntent)
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    101
                )
            }

        }
        binding.stopButton.setOnClickListener {
            stopService(serviceIntent)
        }



        binding.oneTimeScheduleButton.setOnClickListener {
            Log.d(LOG_TAG, "Value " + binding.editTextDate.text)
            val constraint = Constraints.Builder()
                .setRequiresCharging(true)
                .build()
            val timeInMillis = calculateDelayInMillis(11,6)
            val request = OneTimeWorkRequestBuilder<MusicWorker>()
                .setConstraints(constraint)
                .build()
            WorkManager.getInstance(this).enqueue(request)

        }

        binding.peroidicTimeScheduleButton.setOnClickListener {
            val request = PeriodicWorkRequestBuilder<MusicWorker>(15, TimeUnit.MINUTES)
                .build()
            WorkManager.getInstance(this).enqueueUniquePeriodicWork(
                "periotic work",
                ExistingPeriodicWorkPolicy.KEEP,
                request
            )
        }


    }

    fun calculateDelayInMillis(hour: Int, minute: Int): Long {
        val nowCalendar = Calendar.getInstance()
        val alarmCalender = Calendar.getInstance().apply {
            set(Calendar.HOUR, hour)
            set(Calendar.MINUTE, minute)
        }
        return (alarmCalender.timeInMillis - nowCalendar.timeInMillis)
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 101 && grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
            ContextCompat.startForegroundService(this, serviceIntent)
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}