package com.jecrc.learning_edge

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.jecrc.learning_edge.R.anim.from_bottom
import com.jecrc.learning_edge.R.anim.from_top
import com.jecrc.learning_edge.R.id.imageView
import com.jecrc.learning_edge.R.id.action_splashScreen_to_home2
import com.jecrc.learning_edge.R.id.action_splashScreen_to_onBoarding
import com.jecrc.learning_edge.R.id.tv_splash
import com.jecrc.learning_edge.R.layout.fragment_splash_screen


@Suppress("UNREACHABLE_CODE")
@SuppressLint("CustomSplashScreen")
class SplashScreen : Fragment() {

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Hide the title bar in the Home Fragment
        activity?.actionBar?.hide()



        // Inflate the layout for this fragment
        Handler(Looper.getMainLooper()).postDelayed({

            if (onBoardingIsFinished()){
                findNavController().navigate(R.id.action_splashScreen_to_home2)

            }else{
                findNavController().navigate(R.id.action_splashScreen_to_onBoarding)
            }

        }, 3000)


        val view = inflater.inflate(R.layout.fragment_splash_screen, container, false)

        val animTop = AnimationUtils.loadAnimation(view.context, R.anim.from_top)
        val animBottom = AnimationUtils.loadAnimation(view.context, R.anim.from_bottom)

        val tvSplash = view.findViewById<TextView>(R.id.tv_splash)
        val imgSplash = view.findViewById<ImageView>(R.id.imageView)

        tvSplash.animation = animBottom
        imgSplash.animation = animTop


        return view
    }


    private fun onBoardingIsFinished(): Boolean{

        val sharedPreferences = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("finished",false)


    }


}


