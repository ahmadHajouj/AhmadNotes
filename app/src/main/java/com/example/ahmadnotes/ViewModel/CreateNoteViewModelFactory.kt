package com.example.ahmadnotes.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ahmadnotes.Model.DatetimeRepository
import com.example.ahmadnotes.Model.NoteRepository

class CreateNoteViewModelFactory(
    private val repository: NoteRepository,
    private val datetimeRepository: DatetimeRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreateNoteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CreateNoteViewModel(repository, datetimeRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
