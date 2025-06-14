package com.example.myapplication.notes.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.notes.local_db.Note
import com.example.myapplication.notes.local_db.NoteDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class NotesViewModel(
    private val noteDao: NoteDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): ViewModel() {
    val noteList = MutableLiveData<List<Note>?>()
    val errorMessage = MutableLiveData<String>()
    fun getNotes() {
        viewModelScope.launch {
            try {
                val list = viewModelScope.async(dispatcher) {
                    noteDao.getAll() // empty
                }.await()
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