package com.example.myapplication.notes.ui

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.notes.local_db.Note
import com.example.myapplication.notes.local_db.NoteDatabase
import com.example.myapplication.others.common.safeApiCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class NotesViewModel: ViewModel() {
    val noteList = MutableLiveData<List<Note>?>()
    val errorMessage = MutableLiveData<String>()
    fun getNotes(context: Context) {
        viewModelScope.launch {
            try {
                val list = viewModelScope.async(Dispatchers.IO) { NoteDatabase.getInstance(context).noteDao().getAll() }.await()
                if (list != null && list.isNotEmpty()) {
                    noteList.value = list
                } else {
                    errorMessage.value = "No Data found"
                }
            } catch (e: Exception) {
                e.printStackTrace()
                errorMessage.value = "Error found" + e.message
            }
        }
    }
}