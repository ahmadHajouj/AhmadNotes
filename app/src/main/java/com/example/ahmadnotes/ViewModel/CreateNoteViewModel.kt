package com.example.ahmadnotes.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ahmadnotes.Model.DatetimeRepository
import com.example.ahmadnotes.Model.Note
import com.example.ahmadnotes.Model.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateNoteViewModel(
    private val repository: NoteRepository,
    private val datetimeRepository: DatetimeRepository
    ) : ViewModel() {

    private val _dateTime = MutableLiveData<String>()
    val dateTime: LiveData<String> = _dateTime

    private val _titleError = MutableLiveData<String?>()
    val titleError: LiveData<String?> = _titleError

    private val _theNoteError = MutableLiveData<String?>()
    val theNoteError: LiveData<String?> = _theNoteError


    fun fetchDateTime() {
        datetimeRepository.fetchDateTime { fetchedDatetime ->
            _dateTime.postValue(fetchedDatetime ?: "Error fetching time")
        }
    }

    fun validTitle(titleText:String): Boolean {
        if (titleText.isEmpty() || titleText == "") {
            _titleError.value = "Required"
            return false
        }
        if (titleText.length < 2) {
            _titleError.value = "less then 2 character"
            return false
        }
        else {
            _titleError.value= null
            return true
        }
    }

    fun validNote(noteText:String): Boolean {

        if (noteText.isEmpty() || noteText == "") {
            _theNoteError.value = "Required"
            return false
        }
        if (noteText.length < 3) {
            _theNoteError.value = "less then 3 character"
            return false
        }
        else{
            _theNoteError.value = null
            return true
        }
    }


    fun insertNoteIfValid(title: String, noteContent: String): Boolean {
        if (validTitle(title) && validNote(noteContent)) {
            val dateTime = _dateTime.value ?: ""
            insertNote(title, dateTime, noteContent)
            return true
        }
        return false
    }

    fun insertNote(title: String, dateTime: String, noteContent: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val note = Note(0, title, dateTime, noteContent)
            repository.insertNote(note)
        }
    }
}
