package com.jecrc.learning_edge


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class HomeFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Show the action bar for this fragment
        activity?.actionBar?.show()



        // Set the title for the title bar in the Home Fragment
        activity?.title = "Learning Edge"

        val view= inflater.inflate(R.layout.fragment_home2, container, false)
        val name = arguments?.getString("name")
        // For example, set the name to a TextView
        val textViewName: TextView = view.findViewById(R.id.userName)
        textViewName.text = "Hello, $name"
       return view
    }
}

