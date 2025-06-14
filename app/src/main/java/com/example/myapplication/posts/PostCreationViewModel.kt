package com.example.myapplication.posts

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.example.myapplication.others.api.ApiRepository
import com.example.myapplication.others.api.Post
import com.example.myapplication.others.common.ResultWrapper
import com.example.myapplication.others.common.safeApiCall
import com.example.myapplication.posts.PostCreationActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PostCreationViewModel: ViewModel() {
    val successMessage = MutableLiveData<String>()
    val failureMessage = MutableLiveData<String>()
    fun createPost(context: Context, title: String, body: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val repository = ApiRepository(context)
            val post = Post(
                title = title,
                body = body
            )
            val result = safeApiCall {
                repository.apiService.createPost(post)
            }
            when(result) {
                is ResultWrapper.Success -> {
                    successMessage.postValue("Created")
                }
                is ResultWrapper.Failure -> {
                    failureMessage.postValue("Unable to create " + result.message)
                }
            }
        }
    }
}