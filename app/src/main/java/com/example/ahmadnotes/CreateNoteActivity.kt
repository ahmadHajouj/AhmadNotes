package com.example.ahmadnotes

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ahmadnotes.databinding.ActivityCreateNoteBinding
import com.google.gson.Gson
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class CreateNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateNoteBinding
    private lateinit var db: NoteDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NoteDBHelper(this)

        fetchDateTime().start()
        titleFocusListener()
        noteFocusListener()


        binding.saveButton.setOnClickListener {
            if(validTitle() == null && validNote() == null ){
                val title = binding.noteTitle.text.toString()
                val dateTime = binding.time.text.toString()
                val theNote = binding.mainEditText.text.toString()
                val note = Note(0, title, dateTime, theNote)
                db.insertNote(note)
                finish()
                Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT).show()
            }else {
                Toast.makeText(this, "Invalid Note", Toast.LENGTH_SHORT).show()
            }
        }

    }


    private fun titleFocusListener(){
        binding.noteTitle.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.noteTitleWorning.text = validTitle()
            }
        }
    }

    private fun validTitle(): String? {
        val titleText = binding.noteTitle.text.toString()
        if (titleText == null || titleText == "") {
            return "Required"
        }
        if (titleText.length < 2) {
            return "less then 2 character"
        }
        else {
            return null
        }
    }

    private fun noteFocusListener(){
        binding.mainEditText.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.mainEditTextWorning.text = validNote()
            }
        }
    }

    private fun validNote(): String? {
        val noteText = binding.mainEditText.text
        if (noteText == null || noteText.toString() == "") {
            return "Required"
        }
        if (noteText.length < 3) {
            return "less then 3 character"
        }
        else{
            return null
        }
    }

    private fun fetchDateTime() :Thread {
        return Thread {
            val url = URL("https://worldtimeapi.org/api/Asia/Riyadh")
            val connection = url.openConnection() as HttpURLConnection

            if (connection.responseCode == 200){
                val inputSystem = connection.inputStream
                val inputStreamReader = InputStreamReader(inputSystem, "UTF-8",)
                val req = Gson().fromJson(inputStreamReader, Request::class.java)
                updateUI(req)
                inputStreamReader.close()
                inputSystem.close()
            }else{
                println("connection filed")
            }
        }
    }

    private fun updateUI(req: Request) {
        runOnUiThread{
            kotlin.run {
                binding.time.text = req.datetime.substring(0 .. 15).replace('T', ' ')
            }
        }
    }
}