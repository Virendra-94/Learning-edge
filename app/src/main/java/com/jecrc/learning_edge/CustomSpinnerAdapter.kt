package com.jecrc.learning_edge

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.annotation.RequiresApi

class CustomSpinnerAdapter(
    context: Context,
    resource: Int,
    items: Array<String>
) : ArrayAdapter<String>(context, resource, items) {

    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    @RequiresApi(Build.VERSION_CODES.M)
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        if (convertView == null) {
            view = inflater.inflate(android.R.layout.simple_spinner_item, parent, false)
        } else {
            view = convertView
        }

        // Set the text color to white for the selected item
        (view as TextView).setTextColor(context.getColor(android.R.color.white))
        view.text = getItem(position)

        return view
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        if (convertView == null) {
            view = inflater.inflate(android.R.layout.simple_spinner_dropdown_item, parent, false)
        } else {
            view = convertView
        }

        // Set the text color to white for the dropdown items
        (view as TextView).setTextColor(context.getColor(android.R.color.white))
        view.text = getItem(position)

        return view
    }
}
