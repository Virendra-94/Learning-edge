package com.jecrc.learning_edge

import android.app.Application
import com.google.firebase.FirebaseApp
//import com.google.firebase.appcheck.FirebaseAppCheck
//import com.google.firebase.appcheck.safetynet.SafetyNetAppCheckProviderFactory

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)

//        // Initialize Firebase App Check
//        FirebaseAppCheck.getInstance().installAppCheckProviderFactory(
//            SafetyNetAppCheckProviderFactory.getInstance()
//        )
    }
}
