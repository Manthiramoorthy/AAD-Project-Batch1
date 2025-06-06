package com.example.myapplication.others.api

data class Post(
    val body: String,
    val id: Int? = null,
    val title: String
)