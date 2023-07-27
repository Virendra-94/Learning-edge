package com.jecrc.learning_edge

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Hz_Rv_Adapter (private val cardview: List<Hz_Rv_Data>):

    RecyclerView.Adapter<Hz_Rv_Adapter.Hz_Rv_DataViewHolder>() {

    class Hz_Rv_DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.hz_text)
        val descTextView: TextView = itemView.findViewById(R.id.hz_desc)
        val imageImageview: ImageView = itemView.findViewById(R.id.hz_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Hz_Rv_DataViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.hz_rv_home2,parent,false)
        return Hz_Rv_DataViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return cardview.size
    }

    override fun onBindViewHolder(holder: Hz_Rv_DataViewHolder, position: Int) {
        val rvlist = cardview[position]
        holder.nameTextView.text = rvlist.name
        holder.descTextView.text = rvlist.desc
        holder.imageImageview.setImageResource(rvlist.image)
    }


}



