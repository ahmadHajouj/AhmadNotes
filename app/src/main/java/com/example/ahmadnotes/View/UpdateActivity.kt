package com.example.ahmadnotes.View

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.ahmadnotes.Model.Note
import com.example.ahmadnotes.ViewModel.UpdateViewModel
import com.example.ahmadnotes.databinding.ActivityUpdateBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateBinding
    private val updateViewModel: UpdateViewModel by viewModels()
    private var noteId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        noteId = intent.getIntExtra("note_id", -1)
        if (noteId == -1){
            finish()
            return
        }

        updateViewModel.fetchNote(noteId)

        updateViewModel.note.observe(this) { note ->
            if (note == null) {
                Toast.makeText(this, "Note not found", Toast.LENGTH_SHORT).show()
                finish()
                return@observe
            }
                binding.updateNoteTitleEditText.setText(note.title)
                binding.oldTime.text = note.datetime
                binding.mainUpdateEditText.setText(note.theNote)

        }


        binding.updateSaveButton.setOnClickListener{
            val newTitle = binding.updateNoteTitleEditText.text.toString()
            val newTheNote = binding.mainUpdateEditText.text.toString()
            val updatedNote = Note(noteId, newTitle, binding.oldTime.text.toString(), newTheNote)

            updateViewModel.updateNote(updatedNote)
            finish()

            Toast.makeText(this, "changes saved", Toast.LENGTH_SHORT).show()
        }
    }
}