package com.example.myapplication.notes.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityNotesBinding
import com.example.myapplication.notes.Constants
import com.example.myapplication.notes.adapter.NotesAdapter
import com.example.myapplication.notes.local_db.NoteDatabase
import com.example.myapplication.notes.viewmodel.NotesViewModel
import com.example.myapplication.notes.viewmodel.NotesViewModelFactory

class NotesActivity : AppCompatActivity() {
    lateinit var binding: ActivityNotesBinding
    lateinit var viewModel: NotesViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val noteDao = NoteDatabase.getInstance(this).noteDao()
        val factory = NotesViewModelFactory(noteDao)
        viewModel = ViewModelProvider(this, factory)[NotesViewModel::class.java]

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.addButton.setOnClickListener {
            val intent = Intent(this, NoteDetailsActivity::class.java)
            intent.putExtra(Constants.SOURCE_KEY, Constants.CREATE_VALUE)
            startActivity(intent)
        }


        viewModel.noteList.observe(this) { list ->
            list?.let {
                binding.errorText.visibility = View.GONE
                val adapter = NotesAdapter(list)
                binding.recyclerViewNotes.adapter = adapter
                binding.recyclerViewNotes.layoutManager = LinearLayoutManager(this,
                    LinearLayoutManager.VERTICAL, false)
            }
        }

        viewModel.errorMessage.observe(this) { message ->
            binding.recyclerViewNotes.visibility = View.GONE
            binding.errorText.text = message
        }
    }

    override fun onStart() {
        viewModel.getNotes()
        super.onStart()
    }
}