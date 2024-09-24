package com.example.ahmadnotes.Model

import android.content.Context


class NoteRepository(context: Context) {
    private val dbHelper = NoteDBHelper(context)

    fun getAllNotes(): List<Note> {
        return dbHelper.getNotes()
    }

    fun insertNote(note: Note) {
        dbHelper.insertNote(note)
    }

    fun updateNote(note: Note) {
        dbHelper.UpdateNote(note)
    }

    fun deleteNote(noteId: Int) {
        dbHelper.deleteNote(noteId)
    }

    fun getNoteById(noteId: Int): Note{
        return dbHelper.getNoteById(noteId)
    }
}
