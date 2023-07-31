//package com.jecrc.learning_edge
//
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import com.jecrc.learning_edge.databinding.ItemNoteBinding
//import com.jecrc.learning_edge.databinding.SubjectNotesExpandableBinding
//import android.view.animation.AnimationUtils
//
//
//
//
//class NotesAdapter(private var notes: List<String>) :
//    RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {
//    // Add a function to update the adapter data when needed
//    // Add a function to update the adapter data when needed
//    fun updateData(newNotes: Map<String, List<String>>) {
//        notes = newNotes
//        notifyDataSetChanged()
//    }
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
//        val inflater = LayoutInflater.from(parent.context)
//        val binding = ItemNoteBinding.inflate(inflater, parent, false)
//        return NotesViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
//        val entry = notes.entries.elementAt(position)
//        val subject = entry.key
//        val notesList = entry.value
//        holder.bind(subject, notesList)
//    }
//
//    override fun getItemCount(): Int {
//        return notes.size
//    }
//
//    inner class NotesViewHolder(private val binding: ItemNoteBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//
//        fun bind(subject: String, notesList: List<String>) {
//            binding.tvNoteName.text = notesList.toString()
//
//            binding.ivDownloadIcon.setOnClickListener {
//                // Handle download icon click here, if needed
//            }
//        }
//    }
//}
//
package com.jecrc.learning_edge

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jecrc.learning_edge.databinding.ItemNoteBinding
import android.view.animation.AnimationUtils

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
            binding.tvNoteName.text = notesList.toString()

            binding.ivDownloadIcon.setOnClickListener {
                // Handle download icon click here, if needed
            }
        }
    }
}
