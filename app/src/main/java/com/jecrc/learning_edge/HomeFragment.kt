package com.jecrc.learning_edge
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class HomeFragment : Fragment() {


    private lateinit var userNameTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home2, container, false)

        userNameTextView = view.findViewById(R.id.userName)

        // Retrieve the name from SharedPreferences
        val sharedPreferences = requireContext().getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val name = sharedPreferences.getString("name", "Unknown")
        userNameTextView.text = "Hello, $name"

        return view
    }

    }
