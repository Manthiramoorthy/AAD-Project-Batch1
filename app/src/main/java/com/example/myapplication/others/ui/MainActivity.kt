package com.example.myapplication.others.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.others.common.Constant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        val sharedPref = getSharedPreferences(Constant.SHAREDPREF_NAME, MODE_PRIVATE)
        if (sharedPref.getBoolean(Constant.IS_LOGGED_IN_KEY, false)) {
            val intent = Intent(this, RecyclerViewActivity::class.java)
            intent.putExtra(Constant.USERNAME_KEY, sharedPref.getString(Constant.USERNAME_KEY, ""))
            startActivity(intent)
        }
        setContentView(binding.root)
        binding.loginButton.setOnClickListener {
//            lifecycleScope.launch(Dispatchers.IO) {
//                for (i in 1..10) {
//                    withContext(Dispatchers.Main) {
//                        binding.textGreeting.text = "Number " + i
//                    }
//                    Log.d("MainActivty",  "Number " + i)
//                    delay(1000)
//                }
//            }

            val username = binding.editTextUsername.text.toString()
            val password = binding.editTextPassword.text.toString()
            Log.d("MainActivity", "Button is clicked")
            Log.d("MainActivity", username + " " + password)
            if (username.length > 7 && password.length > 7) {
                val intent = Intent(this, RecyclerViewActivity::class.java)
                intent.putExtra(Constant.USERNAME_KEY, username)

                sharedPref.edit {
                    putBoolean(Constant.IS_LOGGED_IN_KEY, true)
                    putString(Constant.USERNAME_KEY, username)
                }
                startActivity(intent)
            } else {

                binding.textGreeting.text = "Invalid Username/Password"
                Toast.makeText(this, "Invalid Username/Password", Toast.LENGTH_LONG).show()
            }
        }
    }
}