package com.jecrc.learning_edge

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


class LecturesFragment : Fragment() {

    private var selectedSemester: String? = null
    private var selectedBranch: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Show the title bar in the Home Fragment
        activity?.actionBar?.show()

        // Set the title for the title bar in the Home Fragment
        activity?.title = "Lectures"

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lectures, container, false)


//        // Retrieve the selected semester and branch from MainActivity2 using the getter methods
//        val mainActivity2 = activity as? MainActivity2
//        val selectedSemester = mainActivity2?.getSelectedSemester()
//        val selectedBranch = mainActivity2?.getSelectedBranch()


    }


}