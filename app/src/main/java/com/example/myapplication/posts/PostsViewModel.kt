package com.example.myapplication.posts

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.others.adapter.PostAdapter
import com.example.myapplication.others.api.ApiRepository
import com.example.myapplication.others.api.Post
import com.example.myapplication.others.common.ResultWrapper
import com.example.myapplication.others.common.safeApiCall
import com.example.myapplication.posts.PostsActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class PostsViewModel(): ViewModel() {
    val successData = MutableLiveData<List<Post>>()
    val failureMessage = MutableLiveData<String>()

    fun updateUI(context: Context) {

        viewModelScope.launch {
            val repository = ApiRepository(context)
            val result =
                viewModelScope.async(Dispatchers.IO) {
                    safeApiCall {
                        repository.apiService.getPost()
                    }
                }.await()
            when(result) {
                is ResultWrapper.Success -> {
                    successData.value = result.data
                }
                is ResultWrapper.Failure -> {
                    failureMessage.value = result.message

                }
            }
        }
    }
}