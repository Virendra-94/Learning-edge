package com.jecrc.learning_edge

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class Home : Fragment() {

    private lateinit var editTextName: AutoCompleteTextView
    private lateinit var spinnerBranch: Spinner
    private lateinit var spinnerSemester: Spinner
    private lateinit var button: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val sharedPref = requireActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE)

        // Check if user details are already saved
        val isUserDataSaved = sharedPref.contains("name") &&
                sharedPref.contains("selectedBranch") &&
                sharedPref.contains("selectedSemester")

        if (isUserDataSaved) {
            // User details are saved, navigate to MainActivity2
            findNavController().navigate(R.id.action_home2_mainActivity2)
            return null
        } else {
            // User details are not saved, show the Home fragment
            val view = inflater.inflate(R.layout.fragment_home, container, false)

            // Find views by their IDs
            editTextName = view.findViewById(R.id.editTextName)
            spinnerBranch = view.findViewById(R.id.spinnerBranch)
            spinnerSemester = view.findViewById(R.id.spinnerSemester)
            button = view.findViewById(R.id.HomeActivity)

            // Setup ArrayAdapter for Branch Spinner
            val branches = arrayOf(
                "Select your branch",
                "CSE",
                "CSE+AI",
                "IT",
                "AI/DS",
                "ECE",
                "Mechanical",
                "Civil"
            ) // Add your branch items here
            val branchAdapter =
                ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, branches)
            branchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerBranch.adapter = branchAdapter

            // Setup ArrayAdapter for Semester Spinner
            val semesters = arrayOf(
                "Select your semester",
                "1st Year",
                "3rd Sem",
                "4th Sem",
                "5th Sem",
                "6th Sem",
                "7th Sem",
                "8th Sem"
            ) // Add your semester items here
            val semesterAdapter =
                ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, semesters)
            semesterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerSemester.adapter = semesterAdapter

            button.setOnClickListener {
                val name = editTextName.text.toString()
                val selectedBranch = spinnerBranch.selectedItem.toString()
                val selectedSemester = spinnerSemester.selectedItem.toString()

                if (name.isNotBlank() && selectedBranch != "Select your branch" && selectedSemester != "Select your semester") {
                    // All fields are filled, proceed to saveUserData and then navigate to MainActivity2
                    val editor = sharedPref.edit()
                    editor.putString("name", name)
                    editor.putString("selectedBranch", selectedBranch)
                    editor.putString("selectedSemester", selectedSemester)
                    editor.apply()

                    // Navigate to MainActivity2
                    val mainActivity2Intent = Intent(requireContext(), MainActivity2::class.java)
                    mainActivity2Intent.putExtra("name", name)
                    mainActivity2Intent.putExtra("selectedBranch", selectedBranch)
                    mainActivity2Intent.putExtra("selectedSemester", selectedSemester)
                    startActivity(mainActivity2Intent)
                    requireActivity().finish()
                } else {
                    // Show a message to the user that they need to fill all fields
                    Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
                }
            }

            return view
        }
    }
}
