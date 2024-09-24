package com.example.ahmadnotes.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ahmadnotes.Model.Note
import com.example.ahmadnotes.Model.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: NoteRepository): ViewModel() {

    private val _notes = MutableLiveData<List<Note>>()
    val note :LiveData<List<Note>> get() = _notes

    fun fetchNotes(){
        viewModelScope.launch(Dispatchers.IO) {
            _notes.postValue(repository.getAllNotes())
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteNote(note.id)
        }
    }

}