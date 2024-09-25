package com.example.ahmadnotes.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ahmadnotes.Model.Note
import com.example.ahmadnotes.Model.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateViewModel @Inject constructor(private val repository: NoteRepository): ViewModel(){

    private val _note = MutableLiveData<Note>()
    val note: LiveData<Note?> = _note

    fun fetchNote (noteId : Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val fetchedNote = repository.getNoteById(noteId)
                _note.postValue(fetchedNote)

        }
    }

    fun updateNote (note: Note){
        viewModelScope.launch {
            repository.updateNote(note)
        }
    }
}