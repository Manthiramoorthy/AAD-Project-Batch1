package com.example.myapplication.notes.ui

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.notes.local_db.Note
import com.example.myapplication.notes.local_db.NoteDao
import com.example.myapplication.notes.local_db.NoteDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class NoteDetailsViewModel(
    val noteDao: NoteDao
): ViewModel() {
    val result = MutableLiveData<Result>()

    fun createNote(title: String, content: String) {
        viewModelScope.launch {
            try {
                val note = Note(
                    title = title,
                    content = content
                )
                viewModelScope.async(Dispatchers.IO) {
                    noteDao.insert(note)
                }.await()
                result.value = Result.Success("Inserted")
            } catch (e: Exception) {
                result.value = Result.Failure("Unable to insert " + e.message)
                e.printStackTrace()
            }
        }
    }

    fun updateNote(id: Int, title: String, content: String) {
        viewModelScope.launch {
            try {
                val note = Note(
                    id = id,
                    title = title,
                    content = content
                )
                viewModelScope.async(Dispatchers.IO) {
                    noteDao.update(note)
                }.await()
                result.value = Result.Success("Updated")
            } catch (e: Exception) {
                result.value = Result.Failure("Unable to update " + e.message)
                e.printStackTrace()
            }
        }
    }

    fun deleteNote(id: Int) {
        viewModelScope.launch {
            try {
                val note = Note(
                    id = id,
                    title = "",
                    content = ""
                )
                viewModelScope.async(Dispatchers.IO) {
                    noteDao.delete(note)
                }.await()
                result.value = Result.Success("Deleted")
            } catch (e: Exception) {
                result.value = Result.Failure("Unable to delete " + e.message)
                e.printStackTrace()
            }
        }
    }
}

sealed class Result() {
    data class Success(val message: String): Result()
    data class Failure(val message: String): Result()
}