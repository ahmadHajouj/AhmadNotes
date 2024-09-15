package com.example.ahmadnotes

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.content.contentValuesOf

class NoteDBHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null,
    DATABASE_VERSION) {

    companion object{
        private const val DATABASE_NAME = "notesapp"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "notes"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_DATETIME = "datetime"
        private const val COLUMN_THE_NOTE = "theNote"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_TITLE TEXT, $COLUMN_DATETIME TEXT, $COLUMN_THE_NOTE TEXT) ")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVerion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertNote (note :Note) {
        val db = writableDatabase
        val value = contentValuesOf().apply {
            put(COLUMN_TITLE, note.title)
            put(COLUMN_DATETIME, note.datetime)
            put(COLUMN_THE_NOTE, note.theNote)
        }
        db.insert(TABLE_NAME, null, value)
        db.close()
    }

    fun getNotes(): List<Note>{
        val notesList = mutableListOf<Note>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
            val datetime = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATETIME))
            val thenote = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_THE_NOTE))

            val note = Note(id, title,datetime,thenote)
            notesList.add(note)
        }
        cursor.close()
        db.close()
        return notesList
    }

    fun UpdateNote(note:Note){
        val db = writableDatabase
        val values = contentValuesOf().apply {
            put(COLUMN_TITLE, note.title)
            put(COLUMN_DATETIME, note.datetime)
            put(COLUMN_THE_NOTE, note.theNote)
        }
        db.update(TABLE_NAME, values, "$COLUMN_ID = ?",arrayOf(note.id.toString()))
        db.close()
    }

    fun getNoteById(noteId: Int):Note{
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $noteId"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
        val datatime = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATETIME))
        val theNote = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_THE_NOTE))

        cursor.close()
        db.close()
        return  Note(id, title, datatime, theNote)
    }

    fun deleteNote (noteId:Int){
        val db = writableDatabase
        db.delete(TABLE_NAME,"$COLUMN_ID = ?",arrayOf(noteId.toString()) )
        db.close()
    }

}