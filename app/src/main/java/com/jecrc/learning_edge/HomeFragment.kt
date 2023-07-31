package com.jecrc.learning_edge
import android.content.ContentValues
import android.content.Context
import android.os.Bundle

import android.os.Handler
import android.os.Looper
=======
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
=======

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import java.util.Timer
import java.util.TimerTask

class HomeFragment : Fragment() {


    // Add RecyclerView related properties
    private lateinit var recyclerView: RecyclerView
    private lateinit var indicatorLayout: LinearLayout
    private lateinit var hzAdapter: Hz_Rv_Adapter
    private var currentIndex = 0
    private val scrollInterval = 3000L
    private val dotIndicatorSize = 16
=======

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


        val view = inflater.inflate(R.layout.fragment_home2, container, false)
        val name = arguments?.getString("name")
        // For example, set the name to a TextView
        val textViewName: TextView = view.findViewById(R.id.userName)
        textViewName.text = "Hello, $name"

        // Find RecyclerView and indicatorLayout from the layout
        recyclerView = view.findViewById(R.id.recyclerView)
        indicatorLayout = view.findViewById(R.id.indicatorLayout)

        // Initialize the RecyclerView and start auto-scrolling
        setupRecyclerView()
        startAutoScroll()
=======
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


    private fun setupRecyclerView() {
        val dummyShowList = listOf(
            Hz_Rv_Data( R.drawable.khush_profile,"LeetCode","leetcode is a platform that is very helpful for the students to practise and enhance skills online.There are more than 1900 programming questions for practising"),
            Hz_Rv_Data(R.drawable.df2_img,"GeeksforGeeks","leetcode is a platform that is very helpful for the students to practise and enhance skills online.There are more than 1900 programming questions for practising"),
            Hz_Rv_Data(R.drawable.df2_img,"HackerRank","leetcode is a platform that is very helpful for the students to practise and enhance skills online.There are more than 1900 programming questions for practising")
            // Add more contacts here if needed
        )

        hzAdapter = Hz_Rv_Adapter(dummyShowList)
        recyclerView.adapter = hzAdapter
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        // Use PagerSnapHelper for snapping behavior
        val snapHelper: SnapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(recyclerView)

        // Add a callback to handle page selection and update indicator
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val centerView = snapHelper.findSnapView(recyclerView.layoutManager)
                    if (centerView != null) {
                        currentIndex = recyclerView.layoutManager?.getPosition(centerView) ?: 0
                        updateIndicator()
                    }
                }
            }
        })

        // Initialize indicator dots
        setupIndicatorDots(dummyShowList.size)
    }

    private fun setupIndicatorDots(count: Int) {
        val dots = mutableListOf<ImageView>()
        val dotParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        dotParams.setMargins(dotIndicatorSize, 0, dotIndicatorSize, 0)

        for (i in 0 until count) {
            val dot = ImageView(requireContext())
            dot.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.drawable_dot_inactive))
            dot.layoutParams = dotParams
            dots.add(dot)
            indicatorLayout.addView(dot)
        }

        // Set the initial selected dot
        dots[currentIndex].setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.drawable_dot_active))
    }

    private fun updateIndicator() {
        val count = indicatorLayout.childCount
        for (i in 0 until count) {
            val dot = indicatorLayout.getChildAt(i) as ImageView
            dot.setImageDrawable(
                if (i == currentIndex) {
                    ContextCompat.getDrawable(requireContext(), R.drawable.drawable_dot_active)
                } else {
                    ContextCompat.getDrawable(requireContext(), R.drawable.drawable_dot_inactive)
                }
            )
        }
    }

    private fun startAutoScroll() {
        val handler = Handler(Looper.getMainLooper())
        val timer = Timer()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                handler.post {
                    currentIndex = (currentIndex + 1) % hzAdapter.itemCount
                    recyclerView.smoothScrollToPosition(currentIndex)
                    updateIndicator()
                }
            }
        }, scrollInterval, scrollInterval)
    }
}
=======
    }

