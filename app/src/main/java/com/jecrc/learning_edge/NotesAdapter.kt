package com.jecrc.learning_edge

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jecrc.learning_edge.databinding.ItemNoteBinding

class NotesAdapter(private var notesData: Map<String, List<String>>) :
    RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    fun updateData(newNotesData: Map<String, List<String>>) {
        notesData = newNotesData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemNoteBinding.inflate(inflater, parent, false)
        return NotesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val entry = notesData.entries.elementAt(position)
        val subject = entry.key
        val notesList = entry.value
        holder.bind(subject, notesList)
    }

    override fun getItemCount(): Int {
        return notesData.size
    }

    inner class NotesViewHolder(private val binding: ItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(subject: String, notesList: List<String>) {
//            binding.tvNoteName.text = notesList.toString()
//
//            binding.ivDownloadIcon.setOnClickListener {
//                // Handle download icon click here, if needed
//            }
        }
    }
}
