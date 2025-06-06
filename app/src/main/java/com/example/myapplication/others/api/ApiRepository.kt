package com.example.myapplication.others.api

import com.example.myapplication.others.common.Constant
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiRepository {
    private val retrofit = Retrofit.Builder()
        .baseUrl(Constant.API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val apiService = retrofit.create(ApiService::class.java)
}