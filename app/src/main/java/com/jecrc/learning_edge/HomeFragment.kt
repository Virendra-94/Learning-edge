package com.jecrc.learning_edge


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        return inflater.inflate(R.layout.fragment_home2, container, false)

    }
}

