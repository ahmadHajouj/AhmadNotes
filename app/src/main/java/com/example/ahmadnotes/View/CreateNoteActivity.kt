package com.example.ahmadnotes.View

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.ahmadnotes.ViewModel.CreateNoteViewModel
import com.example.ahmadnotes.databinding.ActivityCreateNoteBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateNoteBinding
    private val createNoteViewModel: CreateNoteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)


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