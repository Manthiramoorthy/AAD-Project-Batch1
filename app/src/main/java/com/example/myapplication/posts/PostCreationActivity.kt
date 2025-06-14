package com.example.myapplication.posts

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
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
import kotlinx.coroutines.launch

class PostCreationActivity : AppCompatActivity() {
    val viewModel: PostCreationViewModel by viewModels()
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
            viewModel.createPost(this, title, content)
        }

        viewModel.successMessage.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            onBackPressedDispatcher.onBackPressed()
        }

        viewModel.failureMessage.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        }
    }
}