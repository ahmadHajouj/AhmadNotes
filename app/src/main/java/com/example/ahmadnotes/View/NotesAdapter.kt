package com.example.ahmadnotes.View

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.ahmadnotes.Model.Note
import com.example.ahmadnotes.R
import com.example.ahmadnotes.ViewModel.MainViewModel

class NotesAdapter(private var notes: MutableList<Note>, private val viewModel: MainViewModel) :
    RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    class NoteViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val titleTextView:TextView = itemView.findViewById(R.id.titleTextView)
        val timeTextView:TextView = itemView.findViewById(R.id.timeTextView)
        val noteTextView:TextView = itemView.findViewById(R.id.noteTextView)
        val updateButton:ImageView = itemView.findViewById(R.id.updateButton)
        val deleteButton:ImageView = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item,parent,false)
        return NoteViewHolder(view)
    }

    override fun getItemCount(): Int = notes.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.titleTextView.text = note.title
        holder.timeTextView.text = note.datetime
        holder.noteTextView.text = note.theNote

        holder.updateButton.setOnClickListener{
            val intent = Intent(holder.itemView.context, UpdateActivity::class.java).apply {
                putExtra("note_id", note.id)
            }
            holder.itemView.context.startActivity(intent)
        }

        holder.deleteButton.setOnClickListener{
            viewModel.deleteNote(note)
            notes.removeAt(position)
            notifyItemRemoved(position)
            Toast.makeText(holder.itemView.context,"Note Deleted", Toast.LENGTH_SHORT).show()
        }
    }

    fun refreshData(newNotes: List<Note>){
        notes.clear()
        notes.addAll(newNotes)
        notifyDataSetChanged()
    }
}
