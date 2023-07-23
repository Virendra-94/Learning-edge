package com.jecrc.learning_edge

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast


class AboutUsFragment : Fragment() {


    @SuppressLint("QueryPermissionsNeeded")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_about_us, container, false)

        val df1linkedin = rootView.findViewById<Button>(R.id.linkedinbtn)
        val df1insta = rootView.findViewById<Button>(R.id.instabtn)
        val df1gmail = rootView.findViewById<Button>(R.id.socialBtn)
        val df2linkedin = rootView.findViewById<Button>(R.id.linkedinbtn2)
        val df2insta = rootView.findViewById<Button>(R.id.instabtn2)
        val df2gmail = rootView.findViewById<Button>(R.id.socialBtn2)
        //df1 linkedin
        df1linkedin.setOnClickListener {
            val url1 =
                "https://www.linkedin.com/in/khushhal-singh-bb01aa227" // Replace this with your desired URL
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url1))
            startActivity(intent)
        }
        //df1 insta
        df1insta.setOnClickListener {
            val url2 =
                "https://instagram.com/khushhalsinghrajput_?igshid=NTc4MTIwNjQ2YQ==" // Replace this with your desired URL
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url2))
            startActivity(intent)
        }
        //df1 gmail
        df1gmail.setOnClickListener {
            val recipientEmail1 =
                "khushhalsinghofficial@gmail.com" // Replace with the recipient's email address
            val subject1 = "Hello from the App" // Replace with the desired subject
            val body1 = "Hi, This is the body of the email." // Replace with the desired email body

            val emailIntent1 = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:$recipientEmail1")
                putExtra(Intent.EXTRA_SUBJECT, subject1)
                putExtra(Intent.EXTRA_TEXT, body1)

            }
            startActivity(emailIntent1)

        }
        //df2 linkedin
        df2linkedin.setOnClickListener {
            val url3 =
                "https://www.linkedin.com/in/virendra-kumar-448184230/" // Replace this with your desired URL
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url3))
            startActivity(intent)
        }
        //df2 insta
        df2insta.setOnClickListener {
            val url4 =
                "https://instagram.com/virendra_kr9?igshid=NTc4MTIwNjQ2YQ==" // Replace this with your desired URL
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url4))
            startActivity(intent)
        }
        // df2 gmail
        df2gmail.setOnClickListener {
            val recipientEmail2 =
                "virendrakumarofficial94@gmail.com" // Replace with the recipient's email address
            val subject2 = "Hello from the App" // Replace with the desired subject
            val body2 = "Hi, This is the body of the email." // Replace with the desired email body

            val emailIntent2 = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:$recipientEmail2")
                putExtra(Intent.EXTRA_SUBJECT, subject2)
                putExtra(Intent.EXTRA_TEXT, body2)
            }
            startActivity(emailIntent2)
        }
        return rootView
    }
    }
