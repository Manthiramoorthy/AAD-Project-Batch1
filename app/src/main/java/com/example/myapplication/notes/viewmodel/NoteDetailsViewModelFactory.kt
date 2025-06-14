package com.example.myapplication.notes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.myapplication.notes.local_db.NoteDao

class NoteDetailsViewModelFactory(
    val noteDao: NoteDao
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return NoteDetailsViewModel(noteDao) as T
    }
}