package com.example.myapplication.notes.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityNotesBinding
import com.example.myapplication.notes.Constants
import com.example.myapplication.notes.adapter.NotesAdapter
import com.example.myapplication.notes.data.Note
import com.example.myapplication.notes.local_db.NoteDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NotesActivity : AppCompatActivity() {
    lateinit var binding: ActivityNotesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
    }

    private suspend fun applyNotes() {
        val list = NoteDatabase.getInstance(this).noteDao().getAll()
        if (!list.isNullOrEmpty()) {
            val adapter = NotesAdapter(list)
            withContext(Dispatchers.Main) {
                binding.recyclerViewNotes.adapter = adapter
                binding.recyclerViewNotes.layoutManager =
                    LinearLayoutManager(this@NotesActivity, LinearLayoutManager.VERTICAL, false)
            }
        } else {
            withContext(Dispatchers.Main) {
                Toast.makeText(this@NotesActivity, "No data found", Toast.LENGTH_LONG).show()
            }
        }
    }
    override fun onStart() {
        lifecycleScope.launch(Dispatchers.IO) {
            applyNotes()
        }
        Log.d("MainActivity", "onStart")
        super.onStart()
    }

    override fun onResume() {
        Log.d("MainActivity", "onResume")
        super.onResume()
    }

    override fun onPause() {
        Log.d("MainActivity", "onPause")
        super.onPause()
    }

    override fun onStop() {
        Log.d("MainActivity", "onStop")
        super.onStop()
    }

    override fun onDestroy() {
        Log.d("MainActivity", "onDestroy")
        super.onDestroy()
    }

    override fun onRestart() {
        Log.d("MainActivity", "onRestart")
        super.onRestart()
    }
}