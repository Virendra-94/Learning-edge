package com.jecrc.learning_edge

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
class MainActivity : AppCompatActivity(), UserDataListener{

    // Reference to the Firebase Realtime Database
    private lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


// Initialize the Firebase Realtime Database
        databaseReference = FirebaseDatabase.getInstance().reference
    }
    override fun saveUserData(name: String, branch: String, semester: String) {
        val userKey = databaseReference.child("users").push().key ?: return
        val userMap = hashMapOf(
            "name" to name,
            "branch" to branch,
            "semester" to semester
        )

        databaseReference.child("users").child(userKey).setValue(userMap)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Data successfully saved
                    Log.d("Firebase", "Data saved successfully")
                } else {
                    // An error occurred while saving the data
                    Log.e("Firebase", "Data save error: ${task.exception}")
                }
            }
}

}