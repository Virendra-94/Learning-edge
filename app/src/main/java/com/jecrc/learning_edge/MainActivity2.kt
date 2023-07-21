package com.jecrc.learning_edge

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity2 : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private var homeFragment: HomeFragment? = null
    private var notesFragment: NotesFragment? = null
    private var pyqsFragment: PyqsFragment? = null
    private var lecturesFragment: LecturesFragment? = null
    private var quizFragment: QuizFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        // Initialize the fragments
        homeFragment = HomeFragment()
        notesFragment = NotesFragment()
        pyqsFragment = PyqsFragment()
        lecturesFragment = LecturesFragment()
        quizFragment = QuizFragment()

        // Show the HomeFragment by default
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, homeFragment!!)
            .commit()

        // Set up the BottomNavigationView
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener(this)

        // Set up the side navigation drawer
        setupNavigationDrawer()
    }

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

//    private fun setupNavigationDrawer() {
//        // Set up the ActionBarDrawerToggle to handle the hamburger icon and side menu
//        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
//        val navigationView: NavigationView = findViewById(R.id.navigationView)
//
//        // Set the hamburger icon for the side navigation menu
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_hamburger)
//
//        // Create an instance of the ActionBarDrawerToggle and set it as the DrawerListener
//        val toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open_drawer, R.string.close_drawer)
//        drawerLayout.addDrawerListener(toggle)
//        toggle.syncState()
//
//        // Set a click listener for the side menu items
//        navigationView.setNavigationItemSelectedListener { menuItem ->
//            when (menuItem.itemId) {
//                R.id.nav_about_us -> {
//                    // Replace the main fragment container with the About Us fragment
//                    supportFragmentManager.beginTransaction()
//                        .replace(R.id.fragmentContainer, AboutUsFragment())
//                        .commit()
//                }
//                R.id.nav_contact_us -> {
//                    // Replace the main fragment container with the Contact Us fragment
//                    supportFragmentManager.beginTransaction()
//                        .replace(R.id.fragmentContainer, ContactUsFragment())
//                        .commit()
//                }
//                R.id.nav_help -> {
//                    // Replace the main fragment container with the Help fragment
//                    supportFragmentManager.beginTransaction()
//                        .replace(R.id.fragmentContainer, HelpFragment())
//                        .commit()
//                }
//            }
//            // Close the side navigation menu after item selection
//            drawerLayout.closeDrawer(GravityCompat.START)
//            true
//        }
//    }

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
}






