package com.jecrc.learning_edge

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.jecrc.learning_edge.screens.FifthScreen
import com.jecrc.learning_edge.screens.FirstScreen
import com.jecrc.learning_edge.screens.FourthScreen
import com.jecrc.learning_edge.screens.SecondScreen
import com.jecrc.learning_edge.screens.ThirdScreen
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator


class OnBoarding : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Hide the title bar in the Home Fragment
        activity?.actionBar?.hide()
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_on_boarding, container, false)


        val fragmentList = arrayListOf<Fragment>(
            FirstScreen(),
            SecondScreen(),
            ThirdScreen(),
            FourthScreen(),
            FifthScreen()
        )

        val adapter = viewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )

        val viewPager = view.findViewById<ViewPager2>(R.id.viewPager)

        viewPager.adapter = adapter
        val indicator = view.findViewById<DotsIndicator>(R.id.dots_indicator)

        indicator.attachTo(viewPager)

        return view
    }

}