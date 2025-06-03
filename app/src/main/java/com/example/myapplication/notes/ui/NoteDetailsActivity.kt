package com.example.myapplication.notes.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
        val title = intent.getStringExtra(Constants.TITLE_KEY)
        val content = intent.getStringExtra(Constants.CONTENT_KEY)
        val source = intent.getStringExtra(Constants.SOURCE_KEY)
        Log.d("NoteDetailsActivity", "title" + title)
        if (source == Constants.LIST_ITEM_VALUE) {
            binding.titleEditText.setText(title)
            binding.contentEditText.setText(content)
        } else {
            binding.saveButton.text = Constants.CREATE_VALUE
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
    }

    suspend fun update(id: Int, title: String, content: String) {
        val note = Note(
            id = id,
            title = title,
            content = content
        )
        NoteDatabase.getInstance(this@NoteDetailsActivity)
            .noteDao().update(note)
        withContext(Dispatchers.Main) {
            Toast.makeText(this@NoteDetailsActivity, "Updated", Toast.LENGTH_LONG).show()
        }
    }

    private suspend fun createNotes(title: String, content: String) {

        val note = Note(
            title = title,
            content = content
        )
        NoteDatabase.getInstance(this@NoteDetailsActivity)
            .noteDao().insert(note)
        withContext(Dispatchers.Main) {
            Toast.makeText(this@NoteDetailsActivity, "Created", Toast.LENGTH_LONG).show()
        }
    }
}