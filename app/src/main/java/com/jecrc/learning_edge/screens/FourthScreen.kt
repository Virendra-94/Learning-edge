package com.jecrc.learning_edge.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import com.jecrc.learning_edge.R


class FourthScreen : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Hide the title bar in the Home Fragment
        activity?.actionBar?.hide()
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_fourth_screen, container, false)

        val next = view.findViewById<TextView>(R.id.tvNext4)
        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)

        next.setOnClickListener {
            viewPager?.currentItem = 4
        }
        return view
    }



}