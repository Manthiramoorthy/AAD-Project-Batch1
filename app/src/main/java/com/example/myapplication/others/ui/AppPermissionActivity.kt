package com.example.myapplication.others.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.databinding.ActivityAppPermissionBinding
import androidx.core.net.toUri
import com.example.myapplication.R

class AppPermissionActivity : AppCompatActivity() {
    lateinit var binding: ActivityAppPermissionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityAppPermissionBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.callButton.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CALL_PHONE),
                    101
                )
            } else {
                call()
            }
        }


        binding.webView.loadUrl("https://www.google.com")

    }

    private fun call()
    {
        val intent = Intent(Intent.ACTION_CALL)
        intent.data = ("tel:" + binding.editTextPhoneNumber.text).toUri()
        startActivity(intent)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
        deviceId: Int
    ) {
        Log.d("AppPermissionActivity", "onRequestPermissionsResult")
        if (requestCode == 101) {
            if (grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
                call()
            } else {
                Toast.makeText(this, "Permission not granted", Toast.LENGTH_LONG).show()
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults, deviceId)
    }
}