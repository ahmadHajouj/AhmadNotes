package com.example.ahmadnotes.View

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.ahmadnotes.Model.DatetimeRepository
import com.example.ahmadnotes.Model.NoteRepository
import com.example.ahmadnotes.ViewModel.CreateNoteViewModel
import com.example.ahmadnotes.ViewModel.CreateNoteViewModelFactory
import com.example.ahmadnotes.databinding.ActivityCreateNoteBinding

class CreateNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateNoteBinding
    private lateinit var createNoteViewModel: CreateNoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = NoteRepository(this)
        val datetimeRepository = DatetimeRepository()
        val factory = CreateNoteViewModelFactory(repository, datetimeRepository)
        createNoteViewModel = ViewModelProvider(this, factory).get(CreateNoteViewModel::class.java)


        createNoteViewModel.fetchDateTime()
        observeViewModel()

        titleFocusListener()
        noteFocusListener()


        binding.saveButton.setOnClickListener {
                val title = binding.noteTitle.text.toString()
                val theNote = binding.mainEditText.text.toString()


            if(createNoteViewModel.insertNoteIfValid(title,theNote) ){
                finish()
                Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT).show()
            }else {
                Toast.makeText(this, "Invalid Note", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun observeViewModel() {
        createNoteViewModel.titleError.observe(this) { error ->
            binding.noteTitleWorning.text = error
        }

        createNoteViewModel.theNoteError.observe(this) { error ->
            binding.mainEditTextWorning.text = error
        }

        createNoteViewModel.dateTime.observe(this)  { dateTime ->
            binding.time.text = dateTime
        }
    }


    private fun titleFocusListener(){
        binding.noteTitle.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                createNoteViewModel.validTitle(binding.noteTitle.text.toString())
            }
        }
    }


    private fun noteFocusListener(){
        binding.mainEditText.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                createNoteViewModel.validNote(binding.mainEditText.text.toString())
            }
        }
    }



}