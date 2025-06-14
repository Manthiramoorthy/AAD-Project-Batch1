package com.example.myapplication.notes.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import android.window.OnBackInvokedDispatcher
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityNoteDetailsBinding
import com.example.myapplication.notes.Constants
import com.example.myapplication.notes.local_db.Note
import com.example.myapplication.notes.local_db.NoteDao
import com.example.myapplication.notes.local_db.NoteDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NoteDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNoteDetailsBinding
    lateinit var viewModel: NoteDetailsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityNoteDetailsBinding.inflate(layoutInflater)


        val noteDao = NoteDatabase.getInstance(this).noteDao()
        val factory = NoteDetailsViewModelFactory(noteDao)
        viewModel = ViewModelProvider(this, factory)[NoteDetailsViewModel::class.java]


        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val title = intent.getStringExtra(Constants.TITLE_KEY)
        val content = intent.getStringExtra(Constants.CONTENT_KEY)
        val source = intent.getStringExtra(Constants.SOURCE_KEY)
        Log.d("NoteDetailsActivity", "title" + title)
        if (source == Constants.LIST_ITEM_VALUE) {
            binding.titleEditText.setText(title)
            binding.contentEditText.setText(content)
        } else {
            binding.saveButton.text = Constants.CREATE_VALUE
            binding.deleteButton.visibility = View.GONE
        }


        binding.attachButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "text/plain" // MIME type
            }
            startActivityForResult(intent, 101)
        }

        binding.saveButton.setOnClickListener {
            val title = binding.titleEditText.text.toString()
            val content = binding.contentEditText.text.toString()
                if (source == Constants.CREATE_VALUE) {
                    viewModel.createNote(title, content)
                } else {
                    val id = intent.getIntExtra(Constants.ID_KEY, 0)
                    viewModel.updateNote(id,  title, content)
                }
        }

        binding.deleteButton.setOnClickListener {
            val id = intent.getIntExtra(Constants.ID_KEY, 0)
            viewModel.deleteNote(id)

        }

        viewModel.result.observe(this) { result ->
            when (result) {
                is Result.Success -> {
                    Toast.makeText(this, result.message, Toast.LENGTH_LONG).show()
                    onBackPressedDispatcher.onBackPressed()
                }
                is Result.Failure -> {
                    Toast.makeText(this, result.message, Toast.LENGTH_LONG).show()                }
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101 && resultCode == RESULT_OK) {
            val uri = data?.data
            if (uri != null) {
                val text = readTextFromUri(uri)
                binding.contentEditText.setText(text)
            }
        }
    }

    private fun readTextFromUri(uri: Uri): String {
        val inputStream = contentResolver.openInputStream(uri)
        return inputStream?.bufferedReader().use { it?.readText() ?: "" }
    }
}