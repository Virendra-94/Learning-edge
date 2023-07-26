package com.jecrc.learning_edge
import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.util.Log
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
        // Show the action bar for this fragment
        activity?.actionBar?.show()
        // Set the title for the title bar in the Home Fragment
        activity?.title = "Learning Edge"
        userNameTextView = view.findViewById(R.id.userName)

        // Retrieve the name from the SharedPreferences
//        val name = activity?.intent?.getStringExtra("name")
        val sharedPref = requireActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val name = sharedPref.getString("name", null)
        // If the name is not null, save it in MainActivity2's userName variable and in SharedPreferences
        val mainActivity = activity as? MainActivity2
        if (name != null && mainActivity != null) {
            mainActivity.userName = name
            mainActivity.saveUserName(name)
        }

        // If the userNameTextView already has a value, display it; otherwise, display the default message
        userNameTextView.text = "Hello, ${mainActivity?.userName}" ?: "Hello, Unknown"

        return view
    }

    }
