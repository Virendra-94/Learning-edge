package com.jecrc.learning_edge

interface UserDataListener {


    fun saveUserData(name: String, branch: String, semester: String)
//    fun getUserNameFromDatabase(callback: (String) -> Unit)
//    // Add this method to pass the retrieved user name to the fragment
//    fun onUserNameRetrieved(name: String)
}