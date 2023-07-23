package com.jecrc.learning_edge
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

        // Retrieve the name from the MainActivity2 through UserDataListener
        val userDataListener = activity as? UserDataListener
        userDataListener?.getUserNameFromDatabase { name ->
            val userNameTextView: TextView = view.findViewById(R.id.userName)
            userNameTextView.text = "Hello, $name"
        }
        return view
    }
}
