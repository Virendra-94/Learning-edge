package com.jecrc.learning_edge

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.content.FileProvider
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.jecrc.learning_edge.databinding.ActivityMainBinding
import de.hdodenhof.circleimageview.BuildConfig.APPLICATION_ID
import java.io.File

import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import androidx.core.content.ContextCompat.startActivity
import android.widget.ProgressBar
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.jecrc.learning_edge.NotificationUtils.openFileIntent
//import kotlin.coroutines.jvm.internal.CompletedContinuation.context
import kotlinx.coroutines.tasks.await

@Suppress("DEPRECATION")
class MainActivity2 : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener{
//    private lateinit var databaseReference: DatabaseReference

    private var homeFragment: HomeFragment? = null
    private var notesFragment: NotesFragment? = null
    private var pyqsFragment: PyqsFragment? = null
    private var lecturesFragment: LecturesFragment? = null
    private var quizFragment: QuizFragment? = null
//    private var selectedBranch: String? = null
//    private var selectedSemester: String? = null
    var userName: String? = null // Store the user name here


    private lateinit var binding: ActivityMainBinding

    companion object {
        const val NOTIFICATION_ID =9874  // Change this to a unique integer value
        const val CHANNEL_ID = "LEN_Channel" // Change this to a unique string value
    }



    private val WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 1001 // You can use any integer value

    private fun requestWriteExternalStoragePermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            WRITE_EXTERNAL_STORAGE_REQUEST_CODE
        )
    }

    private val STORAGE_PERMISSION_CODE = 1001

    private fun requestStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    STORAGE_PERMISSION_CODE
                )
            } else {
                // Permission already granted, you can proceed with the download
            }
        }
    }

    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, you can proceed with the download
            } else {
                // Permission denied, show a message to the user or handle it accordingly
            }
        }
    }





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)



//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)

        // Get the name, selectedBranch, and selectedSemester from intent
        val name = intent.getStringExtra("name")
        val selectedBranch = intent.getStringExtra("selectedBranch")
        val selectedSemester = intent.getStringExtra("selectedSemester")


        // Initialize the fragments
        homeFragment = HomeFragment()
        notesFragment = NotesFragment()
        pyqsFragment = PyqsFragment()
        lecturesFragment = LecturesFragment()
        quizFragment = QuizFragment()


//        // Initialize the shared preferences only once
//        val sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE)
//        if (!sharedPreferences.contains("selected_branch") && !sharedPreferences.contains("selected_semester")) {
//            with(sharedPreferences.edit()) {
//                putString("selected_branch", branch)
//                putString("selected_semester", semester)
//                apply()
//            }
//        }

        // Store the user name in a class-level variable
        userName = name
//        // Check if the name is already saved, then retrieve it
//        if (sharedPreferences.contains("user_name")) {
//            userName = sharedPreferences.getString("user_name", null)
//        }
        // Create the notification channel (For Android 8.0 and above)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Channel Name"
            val descriptionText = "Channel Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
        // Set up the BottomNavigationView
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener(this)

        // Set up the side navigation drawer
        setupNavigationDrawer()

//        // Pass the name to the HomeFragment
//        val bundle = Bundle()
//        bundle.putString("name", name)
//        homeFragment?.arguments = bundle
//
//        // Pass the selected semester to the NotesFragment using a Bundle
//        val notesBundle = Bundle()
//        notesBundle.putString("selectedSemester", semester)
//        notesFragment?.arguments = notesBundle
//
        // Pass the name and other data to the HomeFragment
        val bundle = Bundle()
        bundle.putString("name", name)
//        bundle.putString("selectedSemester", selectedSemester)
//        bundle.putString("selectedBranch", selectedBranch)
        homeFragment?.arguments = bundle

        val semandbranch = Bundle()
        semandbranch.putString("selectedSemester", selectedSemester)
        semandbranch.putString("selectedBranch", selectedBranch)
        notesFragment?.arguments=semandbranch

        // Show the HomeFragment by default
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, homeFragment!!)
            .commit()




    }
    private fun getSelectedSemester(): String? {
        val sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        return sharedPreferences.getString("selected_semester", null)
    }

    private fun getSelectedBranch(): String? {
        val sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        return sharedPreferences.getString("selected_branch", null)
    }
    // In the onResume method of MainActivity2
    override fun onResume() {
        super.onResume()
        val homeFragment = homeFragment
        val userName = userName
        if (homeFragment != null && userName != null) {
            val bundle = Bundle()
            bundle.putString("name", userName)
            homeFragment.arguments = bundle
        }
    }
    // Function to show a notification
//    fun showNotification(title: String, message: String) {
//        val notificationManager =
//            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        val channelId = "channelId"
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val channel =
//                NotificationChannel(channelId, "Channel Name", NotificationManager.IMPORTANCE_DEFAULT)
//            notificationManager.createNotificationChannel(channel)
//        }
//        val notificationBuilder = NotificationCompat.Builder(this, channelId)
//            .setContentTitle(title)
//            .setContentText(message)
//            .setSmallIcon(R.drawable.ic_notification)
//        notificationManager.notify(1, notificationBuilder.build())
//    }
    fun showNotification(context: Context, title: String, message: String, uri: Uri?) {
        // Create a PendingIntent to open the file when the notification is clicked
        val contentIntent = uri?.let {
            PendingIntent.getActivity(
                context,
                0,
                openFileIntent(context, it),
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        }

        // Create the notification
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentIntent(contentIntent)
            .setAutoCancel(true)

        // Show the notification
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID, builder.build())
    }
    // Function to open the downloaded file
//    fun openFile(localFile: File) {
//        val intent = Intent(Intent.ACTION_VIEW)
//        val uri = FileProvider.getUriForFile(this, "$APPLICATION_ID.fileprovider", localFile)
//        intent.setDataAndType(uri, "application/pdf")
//        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
//        startActivity(intent)
//    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle item selection in the BottomNavigationView
        val fragment: Fragment = when (item.itemId) {
            R.id.nav_home -> homeFragment!!
            R.id.nav_notes -> notesFragment!!
            R.id.nav_pyqs -> pyqsFragment!!
            R.id.nav_lectures -> lecturesFragment!!
            R.id.nav_quiz -> quizFragment!!
            else -> homeFragment!! // Default to homeFragment
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()

        return true
    }


    // Pass the name to the HomeFragment when needed




    private fun setupNavigationDrawer() {
        // Set up the ActionBarDrawerToggle to handle the hamburger icon and side menu
        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
        val navigationView: NavigationView = findViewById(R.id.navigationView)

        // Set the hamburger icon for the side navigation menu
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_hamburger)

        // Create an instance of the ActionBarDrawerToggle and set it as the DrawerListener
        val toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open_drawer, R.string.close_drawer)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Set a click listener for the side menu items
        navigationView.setNavigationItemSelectedListener { menuItem ->
            val fragment: Fragment = when (menuItem.itemId) {
                R.id.nav_about_us -> AboutUsFragment()
                R.id.nav_contact_us -> ContactUsFragment()
                R.id.nav_help -> HelpFragment()
                else -> homeFragment!! // Default to homeFragment
            }

            // Replace the main fragment container with the selected fragment
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit()

            // Close the side navigation menu after item selection
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle the click on the hamburger icon and open the side navigation drawer
        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
        return if (item.itemId == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START)
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    private fun saveSelectedBranchAndSemester(branch: String, semester: String) {
        val sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("selected_branch", branch)
        editor.putString("selected_semester", semester)
        editor.apply()
    }

    // Function to save the user name in SharedPreferences
    fun saveUserName(name: String) {
        val sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("user_name", name)
        editor.apply()
    }
}





class FirebaseManager(private val context: Context) {
    private val storage = FirebaseStorage.getInstance()
    private val storageRef = storage.getReferenceFromUrl("gs://learning-edge-a788e.appspot.com/")
    fun downloadNotes(
        noteName: String,
        onSuccess: (File) -> Unit,
        onFailure: (Exception) -> Unit,
        onProgress: ((Int) -> Unit)? = null
    ) {
        if (context == null) {
            onFailure(IllegalStateException("Context is null"))
            return
        }

        // Get the external downloads directory
        val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)

        // Create the file to save the downloaded note
        val file = File(downloadsDir, noteName)

        val notesRef: StorageReference = storageRef.child("Common").child(noteName)

        val downloadTask = notesRef.getFile(file)

        downloadTask.addOnSuccessListener {
            // File downloaded successfully
            onSuccess(file)
        }.addOnFailureListener { exception ->
            // An error occurred during the download
            onFailure(exception)
        }.addOnProgressListener { taskSnapshot ->
            // Get the download progress and update the ProgressBar
            val progress = (100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount).toInt()
            onProgress?.invoke(progress)
        }
    }
}


//class FirebaseManager {
//    private val storageReference = Firebase.storage.reference
//
//    fun downloadNotes(
//        noteName: String,
//        onSuccess: (File) -> Unit,
//        onFailure: (Exception) -> Unit,
//        onProgress: (Int) -> Unit // Add the onProgress callback
//    ) {
//        val storageRef = Firebase.storage.reference
//        val fileRef = storageRef.child("Common/$noteName")
//
//        val localFile = File.createTempFile(noteName, "pdf")
//        fileRef.getFile(localFile)
//            .addOnSuccessListener {
//                onSuccess(localFile)
//            }
//            .addOnFailureListener { exception ->
//                onFailure(exception)
//            }
//            .addOnProgressListener { taskSnapshot ->
//                // Calculate the progress percentage
//                val progress = (100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount).toInt()
//                onProgress(progress)
//            }
//    }
//}




