package com.example.myapplication.notes.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.notes.local_db.NoteDao

class NotesViewModelFactory(
    val noteDao: NoteDao
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NotesViewModel(noteDao) as T
    }
}