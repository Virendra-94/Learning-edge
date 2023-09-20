package com.jecrc.learning_edge

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import java.util.Timer
import java.util.TimerTask

class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var indicatorLayout: LinearLayout
    private lateinit var hzAdapter: Hz_Rv_Adapter
    private var currentIndex = 0
    private val scrollInterval = 3000L
    private val dotIndicatorSize = 16

    private lateinit var userNameTextView: TextView

    private var timer: Timer? = null // Declare a Timer variable

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home2, container, false)
        activity?.actionBar?.show()
        activity?.title = "Learning Edge"

        val name = arguments?.getString("name")
        val textViewName: TextView = view.findViewById(R.id.userName)
        textViewName.text = "Hello 👋, $name"

        recyclerView = view.findViewById(R.id.recyclerView)
        indicatorLayout = view.findViewById(R.id.indicatorLayout)

        setupRecyclerView()
        startAutoScroll()

        userNameTextView = view.findViewById(R.id.userName)

        val sharedPref = requireActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val mainActivity = activity as? MainActivity2
        if (name != null && mainActivity != null) {
            mainActivity.userName = name
            mainActivity.saveUserName(name)
        }

        userNameTextView.text = "Hello👋, ${mainActivity?.userName}" ?: "Hello, Unknown"


        val card1 = view.findViewById<CardView>(R.id.cardView1)
        val card2 = view.findViewById<CardView>(R.id.cardView2)
        // Assuming you have multiple CardViews with unique IDs in your fragment layout
        card1.setOnClickListener {
            openUrl("https://takeuforward.org/strivers-a2z-dsa-course/strivers-a2z-dsa-course-sheet-2/")
        }

        card2.setOnClickListener {
            openUrl("https://t.me/CODE_Init")
        }

//        cardView3.setOnClickListener {
//            openUrl("https://www.example.com/page3")
//        }


        return view
    }

    private fun openUrl(url: String) {
        // Create an Intent to open a web browser
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))

        // Start the activity with the created Intent
        startActivity(intent)
    }


    private fun setupRecyclerView() {
        val dummyShowList = listOf(
            Hz_Rv_Data( R.drawable.ic_leet_code,"LeetCode","LeetCode is a platform that is very helpful for the students to practise and enhance skills online.There are more than 1900 programming questions for practising"),
            Hz_Rv_Data(R.drawable.ic_gfg,"GeeksforGeeks","GeeksforGeeks is a leading platform that provides computer science resources and coding challenges for programmers and technology enthusiasts."),
            Hz_Rv_Data(R.drawable.ic_hacker_rank,"HackerRank","HackerRank is a place where programmers from all over the world come together to solve problems in a wide range of Computer Science domains.")
        )

        hzAdapter = Hz_Rv_Adapter(dummyShowList)
        recyclerView.adapter = hzAdapter
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        val snapHelper: SnapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(recyclerView)

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

        dots[currentIndex].setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.drawable_dot_active))
    }

    private fun updateIndicator() {
        val count = indicatorLayout.childCount
        for (i in 0 until count) {
            val dot = indicatorLayout.getChildAt(i) as ImageView
            dot.setImageDrawable(
                if (isAdded) {
                    if (i == currentIndex) {
                        ContextCompat.getDrawable(requireContext(), R.drawable.drawable_dot_active)
                    } else {
                        ContextCompat.getDrawable(requireContext(), R.drawable.drawable_dot_inactive)
                    }
                } else {
                    null
                }
            )
        }
    }

    private fun startAutoScroll() {
        val handler = Handler(Looper.getMainLooper())
        timer = Timer()
        timer?.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                handler.post {
                    currentIndex = (currentIndex + 1) % hzAdapter.itemCount
                    recyclerView.smoothScrollToPosition(currentIndex)
                    updateIndicator()
                }
            }
        }, scrollInterval, scrollInterval)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        timer?.cancel() // Cancel the timer to avoid memory leaks
    }
}





