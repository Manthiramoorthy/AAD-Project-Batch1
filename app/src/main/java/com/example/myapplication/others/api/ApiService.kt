package com.example.myapplication.others.api

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @GET("posts")
    suspend fun getPost(): List<Post>

    @POST("posts")
    suspend fun createPost(@Body post: Post): Post?
}

