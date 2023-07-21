package com.jecrc.learning_edge

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
//import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class NotesFragment : Fragment() {
//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        setHasOptionsMenu(true) // Enable options menu for this fragment
//        val toolbar: Toolbar = requireView().findViewById(R.id.toolbar) // Use androidx.appcompat.widget.Toolbar
//        (activity as AppCompatActivity).setSupportActionBar(toolbar)
//        // Other fragment initialization code
//    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Show the action bar for this fragment
        activity?.actionBar?.show()



        // Set the title for the title bar in the Home Fragment
        activity?.title = "Notes"


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notes, container, false)
    }


}