package com.example.myapplication.notes

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityNotesBinding

class NotesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val list = listOf(
            Note(
                "Title - Sample title",
                "Title - Sample Description - fjsh , 37783y243"
            ),
            Note(
                "Title - Sample title",
                "Title - Sample Description - fjsh , 37783y243"
            ),
            Note(
                "Title - Sample title",
                "Title - Sample Description - fjsh , 37783y243"
            ),
            Note(
                "Title - Sample title",
                "Title - Sample Description - fjsh , 37783y243"
            )
        )

        val adapter = NotesAdapter(list)
        binding.recyclerViewNotes.adapter = adapter
        binding.recyclerViewNotes.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }
}