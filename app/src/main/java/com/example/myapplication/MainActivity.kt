package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.loginButton.setOnClickListener {
            val username = binding.editTextUsername.text.toString()
            val password = binding.editTextPassword.text.toString()
            Log.d("MainActivity", "Button is clicked")
            Log.d("MainActivity", username + " " + password)
            if (username.length > 7 && password.length > 7) {
                val intent = Intent(this, RecyclerViewActivity::class.java)
                intent.putExtra("Username", username)

                startActivity(intent)
            } else {

                binding.textGreeting.text = "Invalid Username/Password"
                Toast.makeText(this, "Invalid Username/Password", Toast.LENGTH_LONG).show()
            }
        }
    }
}