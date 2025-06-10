package com.example.myapplication.others.ui.posts

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityPostsBinding
import com.example.myapplication.others.adapter.PostAdapter
import com.example.myapplication.others.api.ApiRepository
import com.example.myapplication.others.common.ResultWrapper
import com.example.myapplication.others.common.safeApiCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PostsActivity : AppCompatActivity() {
    lateinit var binding: ActivityPostsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPostsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.createButton.setOnClickListener {
            val intent = Intent(this, PostCreationActivity::class.java)
            startActivity(intent)
        }
        binding.refreshButton.setOnClickListener {
            updateUI()
        }
    }

    private fun updateUI() {
        lifecycleScope.launch {
            val repository = ApiRepository(this@PostsActivity)
            val result =
                lifecycleScope.async(Dispatchers.IO) {
                    safeApiCall {
                        repository.apiService.getPost()
                    }
                }.await()
            when(result) {
                is ResultWrapper.Success -> {
                    binding.errorCard.visibility = View.GONE
                    binding.recyclerViewPosts.adapter = PostAdapter(result.data)
                    binding.recyclerViewPosts.layoutManager =
                        LinearLayoutManager(this@PostsActivity, LinearLayoutManager.VERTICAL, false)
                }
                is ResultWrapper.Failure -> {
                    Toast.makeText(this@PostsActivity, result.message, Toast.LENGTH_LONG).show()
                    binding.errorText.text = result.message
                    binding.errorCard.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onStart() {
        updateUI()
//        lifecycleScope.launch(Dispatchers.IO) {
//            val list = ApiRepository.apiService.getPost()
//            withContext(Dispatchers.Main) {
//                binding.recyclerViewPosts.adapter = PostAdapter(list)
//                binding.recyclerViewPosts.layoutManager =
//                    LinearLayoutManager(this@PostsActivity, LinearLayoutManager.VERTICAL, false)
//            }
//        }



        super.onStart()
    }
}