package com.example.ahmadnotes.View

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ahmadnotes.Model.NoteRepository
import com.example.ahmadnotes.ViewModel.MainViewModel
import com.example.ahmadnotes.ViewModel.MainViewModelFactory
import com.example.ahmadnotes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var notesAdapter: NotesAdapter
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = NoteRepository(this)
        val factory = MainViewModelFactory(repository)
        mainViewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)


        setupRecyclerView()

        mainViewModel.note.observe(this){
            notes -> notesAdapter.refreshData(notes)
        }

        mainViewModel.fetchNotes()



        binding.addButton.setOnClickListener{
            val intent = Intent(this, CreateNoteActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        mainViewModel.fetchNotes()
    }

    private fun setupRecyclerView(){
        notesAdapter = NotesAdapter(mutableListOf(), mainViewModel)

        binding.notesRecyclerView.apply {
        layoutManager = LinearLayoutManager(this@MainActivity)
        adapter = notesAdapter

        }
    }
}