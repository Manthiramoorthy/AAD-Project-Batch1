package com.example.myapplication

import android.annotation.SuppressLint
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

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.loginButton)
        val userNameText = findViewById<EditText>(R.id.editTextUsername)
        val passwordText = findViewById<EditText>(R.id.editTextPassword)
        val greetingText = findViewById<TextView>(R.id.textGreeting)
        val imageView = findViewById<ImageView>(R.id.imageView)
        button.setOnClickListener {
            val username = userNameText.text.toString()
            val password = passwordText.text.toString()
            Log.d("MainActivity", "Button is clicked")
            Log.d("MainActivity", username + " " + password)
            if (username.length > 7 && password.length > 7) {
                imageView.setImageResource(R.drawable.nature)
                greetingText.text = "Hi " + username
                greetingText.setBackgroundColor(Color.GREEN)

                Toast.makeText(this, "Hi " + username, Toast.LENGTH_LONG).show()

            } else {
                imageView.setImageResource(R.drawable.forest)
                greetingText.setBackgroundColor(Color.RED)
                greetingText.text = "Invalid Username/Password"
                Toast.makeText(this, "Invalid Username/Password", Toast.LENGTH_LONG).show()
            }
        }
    }
}