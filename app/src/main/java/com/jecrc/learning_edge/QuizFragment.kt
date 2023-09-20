
package com.jecrc.learning_edge

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

class QuizFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

//        Show the title bar in the Home Fragment
                activity?.actionBar?.show()

        // Set the title for the title bar in the Home Fragment
        activity?.title = "Quiz"




        val view = inflater.inflate(R.layout.fragment_quiz, container, false)
// Retrieve the selected semester and branch from SharedPreferences
        val sharedPref = requireActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val selectedSemester = sharedPref.getString("selectedSemester", null)
        val selectedBranch = sharedPref.getString("selectedBranch", null)
//        // Get the subjects for the selected branch and semester (you can modify this)
//        val branch = "CSE"
//        val semester = "3rd Sem"
        val subjects = getSubjectsForBranchAndSemester(selectedBranch, selectedSemester)

        // Initialize the subject container
        val subjectContainer = view.findViewById<LinearLayout>(R.id.fragment_quiz_container)

//        // Get the subjects for the selected semester and branch
//        val subjects = getSubjectsForSemesterAndBranch(selectedSemester, selectedBranch)

        // Create subject buttons dynamically
        for (subject in subjects) {
            val subjectBtn = Button(requireContext())
            subjectBtn.text = subject
            subjectBtn.setBackgroundResource(R.drawable.subject_btn_style) // Apply the custom style
            subjectBtn.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))

            // Create layout parameters for the Button
            val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, // Width
                LinearLayout.LayoutParams.WRAP_CONTENT // Height
            )

// Set margins for the Button (left, top, right, bottom)
            layoutParams.setMargins(10, 0, 10, 16) // Adjust the margins as needed

// Set the layout parameters for the Button
            subjectBtn.layoutParams = layoutParams

            subjectBtn.setOnClickListener {
                // Handle button click, start QuestionListActivity with selected subject
                val intent = Intent(requireContext(), QuestionListActivity::class.java)
                intent.putExtra("selectedSubject", subject)
                startActivity(intent)
            }
            // Add the subject button to the container
            subjectContainer.addView(subjectBtn)
        }




        return view
    }
    private fun getSubjectsForBranchAndSemester(selectedBranch: String?, selectedSemester: String?): List<String> {
        // Define subjects based on branch and semester (modify as needed)
        val subjectsMap: Map<String, List<String>> = mapOf(
            "CSE 1st Year" to listOf(
                "Engineering Mathematics-I",
                "Engineering Chemistry" ,
                "Human Values" ,
                "Programming for Problem Solving" ,
                "Basic Civil Engineering",
                "Engineering Mathematics-II" ,
                "Engineering Physics" ,
                "Communication Skills" ,
                "Basic Mechanical Engineering" ,
                "Basic Electrical Engineering"
            ),
            "CSE 3rd Sem" to listOf(
                "Advanced Engineering Mathematics",
                "Managerial Economics and Financial Accounting" ,
                "Digital Electronics" ,
                "Data Structures and Algorithms" ,
                "Object Oriented Programming",
                "Software Engineering"
            ),
            "CSE 4th Sem" to listOf(
                "Discrete Mathematics Structure" ,
                "Technical Communication" ,
                "Microprocessor & Interfaces" ,
                "Database Management System" ,
                "Theory Of Computation",
                "Data Communication and Computer Networks"
            ),
            "CSE 5th Sem" to listOf(
                "Analysis of Algorithms" ,
                "Compiler Design" ,
                "Computers Graphics and Multimedia" ,
                "Information Theory and Coding" ,
                "Operating Systems" ,
                "Wireless Communication"
            ),
            "CSE 6th Sem" to listOf(
                "Artificial Intelligence" ,
                "Cloud Computing" ,
                "Computer Architecture and Organization" ,
                "Digital Image Processing" ,
                "Distributed System" ,
                "Information Security System" ,
                "Machine Learning"
            ),
            "CSE 7th Sem" to listOf(
                "Human Engineering and Safety",
                "Internet Of Things"
            ),
            "CSE 8th Sem" to listOf(
                "Big Data and Analytics" ,
                "Disaster Management"
            ),
        )

        val key = "$selectedBranch $selectedSemester"
        return subjectsMap[key] ?: emptyList()
    }


    private fun navigateToQuestionListFragment(selectedSubject: String) {
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()

        // Create an instance of the QuestionListFragment and pass the selected subject
        val questionListFragment = QuestionListFragment()
        val bundle = Bundle()
        bundle.putString("selectedSubject", selectedSubject)
        questionListFragment.arguments = bundle

        // Replace the current fragment with the QuestionListFragment
        fragmentTransaction.replace(R.id.fragmentContainer, questionListFragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}