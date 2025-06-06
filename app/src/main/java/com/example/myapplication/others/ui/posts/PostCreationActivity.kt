package com.example.myapplication.others.ui.posts

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityNoteDetailsBinding
import com.example.myapplication.others.api.ApiRepository
import com.example.myapplication.others.api.Post
import com.example.myapplication.others.common.ResultWrapper
import com.example.myapplication.others.common.safeApiCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PostCreationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityNoteDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.saveButton.setOnClickListener {
            val title = binding.titleEditText.text.toString()
            val content = binding.contentEditText.text.toString()
            val post = Post(
                title = title,
                body = content
            )
            lifecycleScope.launch(Dispatchers.IO) {
                val result = safeApiCall {
                    ApiRepository.apiService.createPost(post)
                }
                when(result) {
                    is ResultWrapper.Success -> {
                        onBackPressedDispatcher.onBackPressed()
                    }
                    is ResultWrapper.Failure -> {
                        Toast.makeText(this@PostCreationActivity, result.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
//            val result = lifecycleScope.async { ApiRepository.apiService.createPost(post) }
//            lifecycleScope.launch {
//                if (result.await() != null) {
//
//                }
//            }
        }
    }
}