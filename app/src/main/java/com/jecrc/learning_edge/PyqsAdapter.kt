package com.jecrc.learning_edge

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jecrc.learning_edge.databinding.ItemPyqsBinding

class PyqsAdapter(private var pyqsData: List<String>) :
    RecyclerView.Adapter<PyqsAdapter.PyqsViewHolder>() {

    fun updateData(newPyqsData: List<String>) {
        pyqsData = newPyqsData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PyqsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPyqsBinding.inflate(inflater, parent, false)
        return PyqsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PyqsViewHolder, position: Int) {
        val pyqName = pyqsData[position]
        holder.bind(pyqName)
    }

    override fun getItemCount(): Int {
        return pyqsData.size
    }

    inner class PyqsViewHolder(private val binding: ItemPyqsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(pyqName: String) {
//            binding.tvPyqName.text = pyqName
        }
    }
}
