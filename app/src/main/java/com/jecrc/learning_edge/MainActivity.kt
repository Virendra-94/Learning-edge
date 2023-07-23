package com.jecrc.learning_edge
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener



class MainActivity : AppCompatActivity(){



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Get the name, branch, and semester from the intent
        val name = intent.getStringExtra("name")
        val branch = intent.getStringExtra("branch")
        val semester = intent.getStringExtra("semester")

        // Save the user data to SharedPreferences if it's not already saved
        val sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        if (!sharedPreferences.contains("name")) {
            val editor = sharedPreferences.edit()
            editor.putString("name", name)
            editor.apply()
        }
    }

}