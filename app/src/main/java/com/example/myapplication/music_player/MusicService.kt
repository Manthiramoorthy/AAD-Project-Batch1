package com.example.myapplication.music_player

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import com.example.myapplication.R

class MusicService : Service() {

    override fun onBind(intent: Intent): IBinder? = null
    lateinit var mediaPlayer: MediaPlayer
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        mediaPlayer = MediaPlayer.create(this, R.raw.sample)
        mediaPlayer.isLooping = true
        mediaPlayer.start()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        mediaPlayer.stop()
        mediaPlayer.release()
        super.onDestroy()
    }
}