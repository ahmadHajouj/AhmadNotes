package com.example.ahmadnotes

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ahmadnotes.databinding.ActivityUpdateBinding

class UpdateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateBinding
    private lateinit var db : NoteDBHelper
    private var noteId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NoteDBHelper(this)

        noteId = intent.getIntExtra("note_id", -1)
        if (noteId == -1){
            finish()
            return
        }

        val note = db.getNoteById(noteId)
        binding.updateNoteTitleEditText.setText(note.title)
        binding.oldTime.text = note.datetime
        binding.mainUpdateEditText.setText(note.theNote)

        binding.updateSaveButton.setOnClickListener{
            val newTitle = binding.updateNoteTitleEditText.text.toString()
            val newTheNote = binding.mainUpdateEditText.text.toString()
            val updatedNote = Note(noteId, newTitle,note.datetime, newTheNote)
            db.UpdateNote(updatedNote)
            finish()
            Toast.makeText(this, "changes saved", Toast.LENGTH_SHORT).show()

        }
    }
}