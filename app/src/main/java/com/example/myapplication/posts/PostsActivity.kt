package com.example.myapplication.posts

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
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
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class PostsActivity : AppCompatActivity() {
    lateinit var binding: ActivityPostsBinding
    val viewModel: PostsViewModel by viewModels()
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
            viewModel.updateUI(this)
        }

        viewModel.successData.observe(this) { data ->
            binding.errorCard.visibility = View.GONE
            binding.recyclerViewPosts.adapter = PostAdapter(data)
            binding.recyclerViewPosts.layoutManager =
                LinearLayoutManager(this@PostsActivity, LinearLayoutManager.VERTICAL, false)
        }

        viewModel.failureMessage.observe(this) { message ->
            Toast.makeText(this@PostsActivity, message, Toast.LENGTH_LONG).show()
            binding.errorText.text = message
            binding.errorCard.visibility = View.VISIBLE
        }

    }


    override fun onStart() {
        viewModel.updateUI(this)

        super.onStart()
    }
}