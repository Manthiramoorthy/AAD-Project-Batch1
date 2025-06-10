package com.example.myapplication.music_player

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.myapplication.R

class MusicForegroundService : Service() {

    override fun onBind(intent: Intent): IBinder? = null
    lateinit var mediaPlayer: MediaPlayer
    override fun onCreate() {
        createNotificationChannel()
        super.onCreate()
    }
    @SuppressLint("ForegroundServiceType")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent?.action == "ACTION_TOGGLE_PLAY") {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.pause()
            } else {
                mediaPlayer.start()
            }
            // Update notification to reflect play/pause
            val notification = createNotification()
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(1, notification)

        } else {
            mediaPlayer = MediaPlayer.create(this, R.raw.sample)
            mediaPlayer.isLooping = true
            mediaPlayer.start()
            val notification = createNotification()
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU)
                startForeground(101, notification)
            else
                startForeground(101, notification, FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK)
        }
        return START_NOT_STICKY
    }

    private fun createNotification(): Notification {
            val pauseIntent = Intent(this, MusicForegroundService::class.java)
            .apply {
                action = "ACTION_TOGGLE_PLAY"
            }
            val pausePendingIntent = PendingIntent.getService(
                this, 0, pauseIntent, PendingIntent.FLAG_IMMUTABLE
            )

            return NotificationCompat.Builder(this, "music_channel")
            .setContentTitle("Playing Music")
            .setSmallIcon(R.drawable.play_icon)
                .addAction(
                    R.drawable.play_icon,
                    if (mediaPlayer.isPlaying) "Pause" else "Play",
                    pausePendingIntent
                )
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build()
    }
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "music_channel",
                "Music Playback",
                NotificationManager.IMPORTANCE_LOW
            )
            getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
        }
    }



    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.stop()
        mediaPlayer.release()
    }
}

// Work manager