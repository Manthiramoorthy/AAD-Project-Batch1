package com.example.myapplication.integrationTest

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.myapplication.notes.local_db.Note
import com.example.myapplication.notes.local_db.NoteDao
import com.example.myapplication.notes.local_db.NoteDatabase
import com.example.myapplication.notes.viewmodel.NotesViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


// Just to see this changes in git commit
@RunWith(AndroidJUnit4::class)
class NotesViewModelIntegrationTest {

    lateinit var viewModel: NotesViewModel
    lateinit var noteDao: NoteDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val database = Room.inMemoryDatabaseBuilder(context, NoteDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        noteDao = database.noteDao()
        viewModel = NotesViewModel(noteDao, Dispatchers.Main)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun GivenCallGetWithoutInserting() = runTest {
        viewModel.getNotes()
        advanceUntilIdle()
        Thread.sleep(1000)
        Assert.assertEquals(viewModel.errorMessage.value, "No Data found")
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun GivenInsertAndCallGetAll() = runTest {
        val note = Note(
            title = "title", content = "content"
        )
        noteDao.insert(note)
        viewModel.getNotes()
        advanceUntilIdle()
        Thread.sleep(1000)
        Assert.assertTrue(viewModel.noteList.value?.size == 1)
    }
}
