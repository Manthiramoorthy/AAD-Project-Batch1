package com.example.myapplication.others.api

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.icu.util.TimeUnit
import android.net.ConnectivityManager
import androidx.annotation.RequiresPermission
import com.example.myapplication.others.common.Constant
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

class ApiRepository(
    private val context: Context
) {

    private val cacheSize = (5 * 1024 * 1024).toLong() // 5 MB
    val cache = Cache(File(context.cacheDir, "http_cache"), cacheSize)
    val client = OkHttpClient.Builder()
        .cache(cache)
        .addInterceptor(offlineCacheInterceptor())
        .addNetworkInterceptor(cacheInterceptor())
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(Constant.API_BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val apiService = retrofit.create(ApiService::class.java)

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    fun offlineCacheInterceptor() = Interceptor {
        var request = it.request()
        if (!isNetworkAvailable()) {
            val cacheControl = CacheControl.Builder()
                .maxStale(1, java.util.concurrent.TimeUnit.DAYS)
                .build()
            request = request.newBuilder()
                    .cacheControl(cacheControl)
                    .build()
        }
        it.proceed(request)
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    @SuppressLint("ServiceCast")
    fun isNetworkAvailable(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo
        return activeNetwork?.isConnected == true
    }

    fun cacheInterceptor() = Interceptor {
        val response = it.proceed(it.request())
        val cacheControl = CacheControl.Builder()
            .maxAge(1, java.util.concurrent.TimeUnit.MINUTES)
            .build()
        response.newBuilder()
            .header("Cache-Control", cacheControl.toString())
            .build()
    }



}