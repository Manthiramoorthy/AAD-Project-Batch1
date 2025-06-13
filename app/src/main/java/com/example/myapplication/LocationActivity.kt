package com.example.myapplication

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresPermission
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.databinding.ActivityLocationBinding
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import java.util.Locale


val LOG_TAG = LocationActivity::class.simpleName
class LocationActivity : AppCompatActivity() {
    lateinit var binding: ActivityLocationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.findMyLocationButton.setOnClickListener {
            if (
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                ) {
                getLocation()
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION),
                    102
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 102 && grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
            getLocation()
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    fun getLocation() {
        val fused = LocationServices.getFusedLocationProviderClient(this)

        val request = LocationRequest.Builder(
            LocationRequest.PRIORITY_HIGH_ACCURACY,
            5000
        ).build()

        val callback = object: LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                val location = result.lastLocation

                val geocoder = Geocoder(this@LocationActivity, Locale.getDefault())
                if (location != null) {
                    val locationDetails = geocoder.getFromLocation(
                        location.latitude,
                        location.longitude,
                        1
                    )

                    val output = "Locality : " + locationDetails?.get(0)?.getAddressLine(0)

                    Log.d(LOG_TAG, output)
                    binding.locationText.text = output
                }
                super.onLocationResult(result)
            }
        }

        fused.requestLocationUpdates(
            request,
            callback,
            Looper.getMainLooper()
        )
    }
}