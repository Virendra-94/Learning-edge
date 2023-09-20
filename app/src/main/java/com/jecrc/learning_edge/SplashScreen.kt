package com.jecrc.learning_edge

import android.annotation.SuppressLint
import android.content.Context
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
        val view = inflater.inflate(R.layout.fragment_splash_screen, container, false)

        val animFadeIn = AnimationUtils.loadAnimation(view.context, R.anim.fade_in)

        val tvSplash = view.findViewById<TextView>(R.id.tv_splash)
        val imgSplash = view.findViewById<ImageView>(R.id.imageView)

        tvSplash.animation = animFadeIn
        imgSplash.animation = animFadeIn

        // Delay navigation based on your requirements
        Handler(Looper.getMainLooper()).postDelayed({
            if (onBoardingIsFinished()) {
                findNavController().navigate(R.id.action_splashScreen_to_home2)
            } else {
                findNavController().navigate(R.id.action_splashScreen_to_onBoarding)
            }
        }, 1500)

        return view
    }

    private fun onBoardingIsFinished(): Boolean {
        val sharedPreferences = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("finished", false)
    }
}




//package com.jecrc.learning_edge
//
//import android.annotation.SuppressLint
//import android.content.Context
//import android.os.Bundle
//import android.os.Handler
//import android.os.Looper
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.view.animation.AnimationUtils
//import android.widget.ImageView
//import android.widget.TextView
//import androidx.fragment.app.Fragment
//import androidx.navigation.fragment.findNavController
//
//
//@Suppress("UNREACHABLE_CODE")
//@SuppressLint("CustomSplashScreen")
//class SplashScreen : Fragment() {
//
//    @SuppressLint("MissingInflatedId")
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//
//        // Hide the title bar in the Home Fragment
//        activity?.actionBar?.hide()
//
//
//
//        // Inflate the layout for this fragment
//        Handler(Looper.getMainLooper()).postDelayed({
//
//            if (onBoardingIsFinished()){
//                findNavController().navigate(R.id.action_splashScreen_to_home2)
//
//            }else{
//                findNavController().navigate(R.id.action_splashScreen_to_onBoarding)
//            }
//
//        }, 2000)
//
//
//        val view = inflater.inflate(R.layout.fragment_splash_screen, container, false)
//
//        val animTop = AnimationUtils.loadAnimation(view.context, R.anim.from_top)
//        val animBottom = AnimationUtils.loadAnimation(view.context, R.anim.from_bottom)
//
//        val tvSplash = view.findViewById<TextView>(R.id.tv_splash)
//        val imgSplash = view.findViewById<ImageView>(R.id.imageView)
//
//        tvSplash.animation = animBottom
//        imgSplash.animation = animTop
//
//
//        return view
//    }
//
//
//    private fun onBoardingIsFinished(): Boolean{
//
//        val sharedPreferences = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
//        return sharedPreferences.getBoolean("finished",false)
//
//
//    }
//
//
//}
//
//
