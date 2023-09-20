package com.jecrc.learning_edge

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.jecrc.learning_edge.NotificationUtils.openFileIntent
import com.jecrc.learning_edge.databinding.ActivityMainBinding
import java.io.File
//import kotlin.coroutines.jvm.internal.CompletedContinuation.context



@Suppress("DEPRECATION")
//class MainActivity2 : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener{
    class MainActivity2 : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener, LecturesFragment.SubjectClickListener {
//    private lateinit var databaseReference: DatabaseReference

    private var homeFragment: HomeFragment? = null
    private var notesFragment: NotesFragment? = null
    private var pyqsFragment: PyqsFragment? = null
    private var lecturesFragment: LecturesFragment? = null
    private var quizFragment: QuizFragment? = null
    private lateinit var toggle: ActionBarDrawerToggle

//    var userName: String? = null // Store the user name here
var userName: String = "" // Initialize as an empty string


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


        // Retrieve the user's name from SharedPreferences
        val sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        userName = sharedPreferences.getString("name", "") ?: ""

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

        // Set labels for each menu item
        val menu = bottomNavigationView.menu
        menu.findItem(R.id.nav_home)?.title = getString(R.string.menu_home)
        menu.findItem(R.id.nav_notes)?.title = getString(R.string.menu_notes)
        menu.findItem(R.id.nav_pyqs)?.title = getString(R.string.menu_pyqs)
        menu.findItem(R.id.nav_lectures)?.title = getString(R.string.menu_lectures)
        menu.findItem(R.id.nav_quiz)?.title = getString(R.string.menu_quiz)

        // Set up the side navigation drawer
        setupNavigationDrawer()

        // Pass the name and other data to the HomeFragment
        val bundle = Bundle()
        bundle.putString("name", name)

        homeFragment?.arguments = bundle

        val semandbranch = Bundle()
        semandbranch.putString("selectedSemester", selectedSemester)
        semandbranch.putString("selectedBranch", selectedBranch)
        notesFragment?.arguments=semandbranch

        // Show the HomeFragment by default
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, homeFragment!!)
            .commit()

        // Set the hamburger icon for the side navigation menu
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_hamburger)



        lecturesFragment?.setSubjectClickListener(this)

    }


    override fun onSubjectClick(subject: Subject) {
        // TODO: Fetch and set the playlist URL for the clicked subject
        val clickedSubjectPlaylistUrl = subject.playlistUrl // Replace with the actual playlist URL

        // Get the selected branch and semester from MainActivity2
        val selectedBranch = getSelectedBranch() // Implement this method to retrieve the selected branch
        val selectedSemester = getSelectedSemester() // Implement this method to retrieve the selected semester

        // Create a new instance of LecturesFragment with the branch, semester, and playlist URL
        val fragment = LecturesFragment.newInstance(clickedSubjectPlaylistUrl)

        // Replace the current fragment with the new fragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null) // Add to the back stack to allow navigation back
            .commit()
    }


    private fun getSelectedSemester(): String? {
        val sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        return sharedPreferences.getString("selectedSemester", null)
    }

    private fun getSelectedBranch(): String? {
        val sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        return sharedPreferences.getString("selectedBranch", null)
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

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle item selection in the BottomNavigationView
        val fragment: Any = when (item.itemId) {
            R.id.nav_home -> homeFragment!!
            R.id.nav_notes -> notesFragment!!
            R.id.nav_pyqs -> pyqsFragment!!
            R.id.nav_lectures -> lecturesFragment!!
            R.id.nav_quiz -> quizFragment!!
            else -> homeFragment!! // Default to homeFragment
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment as Fragment)
            .commit()

        return true
    }


    private fun setupNavigationDrawer() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
        val navigationView: NavigationView = findViewById(R.id.navigationView)

        // Create an instance of the ActionBarDrawerToggle and set it as the DrawerListener
        toggle = object : ActionBarDrawerToggle(
            this,
            drawerLayout,
            R.string.open_drawer,
            R.string.close_drawer
        ) {
            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
                // Update the icon to the back arrow when the drawer is opened
                supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back_arrow)
            }

            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
                // Update the icon to the hamburger icon when the drawer is closed
                supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_hamburger)
            }
        }

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState() // This line is important for the icon to change

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
        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)

        when (item.itemId) {
            android.R.id.home -> {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START)
                } else {
                    drawerLayout.openDrawer(GravityCompat.START)
                }
                return true
            }
            else -> return super.onOptionsItemSelected(item)
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

        val notesRef: StorageReference = storageRef.child("Notes").child(noteName)

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




    fun downloadPyqs(
        branch: String?,
        semester: String?,
        pyqsFileName: String,
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

        // Create the file to save the downloaded pyqs
        val file = File(downloadsDir, pyqsFileName)

        val pyqsRef: StorageReference = storageRef.child("Notes").child(pyqsFileName)

        val downloadTask = pyqsRef.getFile(file)

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








