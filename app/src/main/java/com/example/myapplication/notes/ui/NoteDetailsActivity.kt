package com.example.myapplication.notes.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import android.window.OnBackInvokedDispatcher
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityNoteDetailsBinding
import com.example.myapplication.notes.Constants
import com.example.myapplication.notes.local_db.Note
import com.example.myapplication.notes.local_db.NoteDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NoteDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNoteDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityNoteDetailsBinding.inflate(layoutInflater)
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
            lifecycleScope.launch(Dispatchers.IO) {
                if (source == Constants.CREATE_VALUE) {
                    createNotes(title, content)
                } else {
                    val id = intent.getIntExtra(Constants.ID_KEY, 0)
                    update(
                        id = id,
                        title = title,
                        content = content
                    )
                }
            }
        }

        binding.deleteButton.setOnClickListener {
            val id = intent.getIntExtra(Constants.ID_KEY, 0)
            val note = Note(id = id, title = "", content = "")
            lifecycleScope.launch(Dispatchers.IO) {
                delete(note)
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



    private suspend fun delete(note: Note) {
        NoteDatabase.getInstance(this@NoteDetailsActivity)
            .noteDao().delete(note)
        withContext(Dispatchers.Main) {
            Toast.makeText(this@NoteDetailsActivity, "Deleted", Toast.LENGTH_LONG).show()
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private suspend fun update(id: Int, title: String, content: String) {
        val note = Note(
            id = id,
            title = title,
            content = content
        )
        NoteDatabase.getInstance(this@NoteDetailsActivity)
            .noteDao().update(note)
        withContext(Dispatchers.Main) {
            Toast.makeText(this@NoteDetailsActivity, "Updated", Toast.LENGTH_LONG).show()
            onBackPressedDispatcher.onBackPressed()
        }
    }

//    override fun onBackPressed() {
//        super.onBackPressed()
//        Log.d("NoteDetailsActivty", "getOnBackInvokedDispatcher")
//        val title = binding.titleEditText.text.toString()
//        val content = binding.contentEditText.text.toString()
//        lifecycleScope.launch(Dispatchers.IO) {
//            createNotes(title, content)
//        }
//    }

    private suspend fun createNotes(title: String, content: String) {

        val note = Note(
            title = title,
            content = content
        )
        NoteDatabase.getInstance(this@NoteDetailsActivity)
            .noteDao().insert(note)
        withContext(Dispatchers.Main) {
            Toast.makeText(this@NoteDetailsActivity, "Created", Toast.LENGTH_LONG).show()
            onBackPressedDispatcher.onBackPressed()
        }
    }
}