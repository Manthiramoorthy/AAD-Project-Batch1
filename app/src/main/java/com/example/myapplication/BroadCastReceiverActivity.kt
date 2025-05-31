package com.example.myapplication

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class BroadCastReceiverActivity : AppCompatActivity() {
    private val connectivityBroadcastReceiver = ConnectivityBroadcastReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_broad_cast_receiver)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val filter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        filter.addAction(Intent.ACTION_BATTERY_OKAY)
        registerReceiver(connectivityBroadcastReceiver, filter)
    }

    override fun onPause() {
//        unregisterReceiver(connectivityBroadcastReceiver)
        super.onPause()
    }
}

class ConnectivityBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        Toast.makeText(p0, "${p1?.action}", Toast.LENGTH_LONG).show()
        Log.d("ConnectivityBroadcastReceiver", "ConnectivityBroadcastReceiver is called ${p1?.action}")
    }
}