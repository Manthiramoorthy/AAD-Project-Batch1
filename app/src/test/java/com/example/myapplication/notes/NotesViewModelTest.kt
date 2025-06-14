package com.example.myapplication.notes

import android.net.http.NetworkException
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.myapplication.notes.local_db.Note
import com.example.myapplication.notes.local_db.NoteDao
import com.example.myapplication.notes.viewmodel.NotesViewModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.hamcrest.core.Every
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NotesViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    private val noteDao = mockk<NoteDao>()

    lateinit var viewModel: NotesViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        viewModel = NotesViewModel(noteDao, Dispatchers.Main)
        Dispatchers.setMain(StandardTestDispatcher())

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Given call getNotes when getAll is empty then errorMessage should be No Data found`() = runTest {
        every { noteDao.getAll() } returns listOf()
        viewModel.getNotes()
        advanceUntilIdle()
        Assert.assertEquals(viewModel.errorMessage.value, "No Data found")
    }

    @Test
    fun `Given call getNotes when getAll is null then errorMessage should be No Data found`() = runTest {
        every { noteDao.getAll() } returns null
        viewModel.getNotes()
        advanceUntilIdle()
        Assert.assertEquals(viewModel.errorMessage.value, "No Data found")
    }

    @Test
    fun `Given call getNotes when getAll is valid list then noteList should be valid list`() = runTest {
        val list = listOf(Note(title = "title", content = "content"))
        every { noteDao.getAll() } returns list
        viewModel.getNotes()
        advanceUntilIdle()
        Assert.assertEquals(viewModel.noteList.value, list)
        Assert.assertEquals(viewModel.errorMessage.value, null)
    }

    @Test
    fun `Given call getNotes when getAll is throwing exception then errorMessage should be contains Error found`() = runTest {
        every { noteDao.getAll() } throws ArithmeticException()
        viewModel.getNotes()
        advanceUntilIdle()
        Assert.assertTrue(viewModel.errorMessage.value?.contains("Error found") == true)
    }

}