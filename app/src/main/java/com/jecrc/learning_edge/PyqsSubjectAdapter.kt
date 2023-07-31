package com.jecrc.learning_edge

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class PyqsSubjectAdapter(private val pyqsList: List<String>) :
    RecyclerView.Adapter<PyqsSubjectAdapter.PyqsSubjectViewHolder>() {

    private var selectedPyqs: String? = null

    // Function to get the selected Pyqs
    fun getSelectedPyqs(): String? {
        return selectedPyqs
    }

    // Interface to handle item click events
    interface OnItemClickListener {
        fun onItemClick(pyqsFile: String)
    }

    // Listener reference
    private var onItemClickListener: OnItemClickListener? = null

    // Function to set the click listener from outside the adapter
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.onItemClickListener = listener
    }

    inner class PyqsSubjectViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val paperLinkTextView: TextView = view.findViewById(R.id.paperLinkTextView)

        fun bind(pyqsFile: String) {
            // ... (Other binding code remains unchanged)

            // Set the click listener for the item view
            view.setOnClickListener {
                onItemClickListener?.onItemClick(pyqsFile)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PyqsSubjectViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.subject_paper_item, parent, false)
        return PyqsSubjectViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PyqsSubjectViewHolder, position: Int) {
        val pyqsFile = pyqsList[position]
        holder.paperLinkTextView.text = pyqsFile

        // Set a click listener for the paper link
        holder.paperLinkTextView.setOnClickListener {
            // Replace this with your implementation to handle the download link click
            // For demonstration purposes, I'll show a toast when the link is clicked
            val context = holder.itemView.context
            Toast.makeText(context, "Clicked on paper link: $pyqsFile", Toast.LENGTH_SHORT).show()
        }
    }

    // Add this method to get the item at a given position
    fun getItem(position: Int): String {
        return pyqsList[position]
    }

    // Implement getItemCount() to return the number of items in the list
    override fun getItemCount(): Int {
        return pyqsList.size
    }
}
