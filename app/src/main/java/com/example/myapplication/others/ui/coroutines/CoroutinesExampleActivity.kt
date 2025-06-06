package com.example.myapplication.others.ui.coroutines

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CoroutinesExampleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_coroutines_example)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
//        lifecycleScope.launch {
//            task1()
//        }
    }

    suspend fun task1() {
        for(i in 1 .. 10) {
            Log.d("CoroutinesExampleActivity", "Task1 i = $i")
        }
    }

    suspend fun task2() {
        for(i in 10000 .. 2000000) {
            Log.d("CoroutinesExampleActivity", "Task2 i = $i")
        }
    }
}