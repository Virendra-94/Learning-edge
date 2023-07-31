//
//package com.jecrc.learning_edge
//
//import android.app.DownloadManager
//import android.content.ContentValues.TAG
//import android.content.Context
//import android.content.Intent
//import android.content.pm.PackageManager
//import android.media.MediaScannerConnection
//import android.net.Uri
//import android.os.Build
//import android.os.Bundle
//import android.os.Environment
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ArrayAdapter
//import android.widget.Button
//import android.widget.ImageView
//import android.widget.LinearLayout
//import android.widget.ProgressBar
//import android.widget.Spinner
//import androidx.core.content.FileProvider
//import androidx.fragment.app.Fragment
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.jecrc.learning_edge.NotificationUtils.showNotification
//import com.jecrc.learning_edge.databinding.FragmentNotesBinding
//import android.webkit.MimeTypeMap
//import android.widget.Toast
//import androidx.core.app.ActivityCompat
//import androidx.core.content.ContextCompat
//import java.io.File
//import java.io.FileInputStream
//import java.io.FileOutputStream
//
//class NotesFragment : Fragment() {
//
//    private lateinit var binding: FragmentNotesBinding
//    private lateinit var notesRecyclerView: RecyclerView
//    private lateinit var notesAdapter: NotesAdapter
//    private lateinit var progressBar: ProgressBar
//
//
//
//
//
//
//    private val STORAGE_PERMISSION_CODE = 1001
//    private lateinit var notesDropdown: Spinner
//    private val WRITE_EXTERNAL_STORAGE_REQUEST= 1001
//    private val WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE
//
//
//    private fun requestStoragePermission() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (ContextCompat.checkSelfPermission(
//                    requireContext(),
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE
//                ) != PackageManager.PERMISSION_GRANTED
//            ) {
//                requestPermissions(
//                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
//                    STORAGE_PERMISSION_CODE
//                )
//            } else {
//                // Permission already granted, you can proceed with the download
//                val selectedNote = notesDropdown.selectedItem as? String
//                selectedNote?.let {
//                    downloadAndShowNote(it)
//                }
//            }
//        }
//    }
//
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        if (requestCode == WRITE_EXTERNAL_STORAGE_REQUEST) {
//            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                // Permission granted, proceed with the download
//                val selectedNote = notesDropdown.selectedItem as? String
//                selectedNote?.let {
//                    downloadNoteFile(requireContext(), it)
//                }
//            } else {
//                // Permission denied, show a message to the user or handle it accordingly
//                // For example, you can show a toast message
//                Toast.makeText(
//                    requireContext(),
//                    "Storage permission denied. Cannot download the file.",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//        }
//    }
//
//
//
//
//
//
//
//
//
//
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//
//        binding = FragmentNotesBinding.inflate(inflater, container, false)
//        notesRecyclerView = binding.notesRecyclerView
//        notesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
//
//        // Initialize the progressBar
//        progressBar = binding.progressBar
//        progressBar.visibility = View.GONE
//
//        // Set LayoutManager for the RecyclerView
//        notesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
//
//        // Retrieve the selected semester and branch from SharedPreferences
//        val sharedPref = requireActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE)
//        val selectedSemester = sharedPref.getString("selectedSemester", null)
//        val selectedBranch = sharedPref.getString("selectedBranch", null)
//// Assuming you have a list of subjects with corresponding notes
//        val subjectsWithNotes = mapOf(
//            "Basic Civil Engineering (BCE)" to listOf("Common_BCE_Unit_1&2.pdf", "Common_BCE_Unit_4.pdf"),
//            "Engineering Chemistry" to listOf("Note A", "Note B")
//            // Add more subjects with their corresponding notes
//        )
//        // Create the adapter and set it to the RecyclerView
////        notesAdapter = NotesAdapter(subjectsWithNotes) // Replace 'yourListOfNotes' with your actual list of notes
////        notesRecyclerView.adapter = notesAdapter
//
////        // Check if the selected semester is "1st Year" and selected branch is not null
////        if (selectedSemester == "1st Year" && selectedBranch != null) {
////            // Set up the expandable sections for each subject button
////            for ((index, subject) in subjectsWithNotes.keys.withIndex()) {
////                val subjectButton = LayoutInflater.from(requireContext())
////                    .inflate(R.layout.subject_notes_expandable, binding.subjectContainer, false) as LinearLayout
////                subjectButton.findViewById<Button>(R.id.btnSubject).text = subject
////                setupExpandableSection(subjectButton, subjectsWithNotes[subject] ?: emptyList())
////                binding.subjectContainer.addView(subjectButton)
////            }
////        }
//// Check if the selected semester is "1st Year" and selected branch is not null
//        if (selectedSemester == "1st Year" && selectedBranch != null) {
//            // Set up the expandable sections for each subject button
//            for ((index, subject) in subjectsWithNotes.keys.withIndex()) {
//                val subjectButton = LayoutInflater.from(requireContext())
//                    .inflate(R.layout.subject_notes_expandable, binding.subjectContainer, false) as LinearLayout
//                subjectButton.findViewById<Button>(R.id.btnSubject).text = subject
//                setupExpandableSection(subjectButton, subjectsWithNotes[subject] ?: emptyList())
//                binding.subjectContainer.addView(subjectButton)
//            }
//
//            // Default to the first subject in the map
//            val firstSubject = subjectsWithNotes.keys.first()
//            val selectedSubjectNotes = subjectsWithNotes[firstSubject] ?: emptyList()
//
//            // Set up the RecyclerView with the list of notes for the selected subject
//            notesAdapter = NotesAdapter(selectedSubjectNotes)
//            notesRecyclerView = binding.notesRecyclerView
//            notesRecyclerView.adapter = notesAdapter
//        }
//        return binding.root
//    }
//
//    private fun setupExpandableSection(linearLayout: LinearLayout, notes: List<String>) {
//        // Find views inside the expandable layout
//        val btnSubject: Button = linearLayout.findViewById(R.id.btnSubject)
//        val expandableSection: LinearLayout = linearLayout.findViewById(R.id.expandableSection)
//        val notesDropdown: Spinner = linearLayout.findViewById(R.id.dropdownNotes)
//        val ivDownloadIcon: ImageView = linearLayout.findViewById(R.id.ivDownloadIcon)
//
//
//
//
//        // Set up the ArrayAdapter for the Spinner
//        val adapter = ArrayAdapter(
//            requireContext(),
//            android.R.layout.simple_spinner_dropdown_item,
//            notes
//        )
//        notesDropdown.adapter = adapter
//
//        // Set a click listener for the download icon
//        ivDownloadIcon.setOnClickListener {
//            val selectedNote = notesDropdown.selectedItem as? String
//            selectedNote?.let {
//                requestStoragePermission() // Request the storage permission first
////                downloadAndShowNote(it)
//            }
//        }
//
//        // Set a click listener for the subject button to toggle the expandable layout
//        btnSubject.setOnClickListener {
//            if (expandableSection.visibility == View.VISIBLE) {
//                expandableSection.visibility = View.GONE
//            } else {
//                expandableSection.visibility = View.VISIBLE
//            }
//        }
//    }
//
//    private fun downloadAndShowNote(noteName: String) {
//        // Show the ProgressBar when the download starts
//        progressBar.visibility = View.VISIBLE
//
//        val context = requireContext()
//
//        val firebaseManager = FirebaseManager(context)
//        firebaseManager.downloadNotes(
//            noteName,
//            onSuccess = { localFile ->
//                // Hide the ProgressBar when the download is complete
//                progressBar.visibility = View.GONE
//
//                // Notify the user that the download is successful
//                val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
//                val newFile = File(downloadsDir, noteName)
//                localFile.copyTo(newFile, true)
//
//                showNotification(
//                    context,
//                    "Notes downloaded",
//                    "File downloaded successfully",
//                    FileProvider.getUriForFile(
//                        context,
//                        "com.jecrc.learning_edge.fileprovider",
//                        newFile
//                    )
//                )
//
//                // Use MediaScanner to add the file to the device's media library
//                MediaScannerConnection.scanFile(
//                    context,
//                    arrayOf(newFile.absolutePath),
//                    null
//                ) { _, _ -> /* No need to do anything here */ }
//            },
//            onFailure = { exception ->
//                // Hide the ProgressBar on failure
//                progressBar.visibility = View.GONE
//                showNotification(
//                    context,
//                    "Error",
//                    "Failed to download notes",
//                    null // Pass null as the file, since the download failed
//                )
//                Log.e(TAG, "Failed to download notes: $exception")
//            },
//            // Update the progress of the ProgressBar during the download
//            onProgress = { progress ->
//                progressBar.progress = progress
//            }
//        )
//    }
//
////    private fun downloadAndShowNote(noteName: String) {
////        // Implement the download and display logic here
////        // Call the FirebaseManager's downloadNotes method and handle the download result
////        Log.d(TAG, "Downloading note: $noteName")
////        val firebaseManager = FirebaseManager()
////        firebaseManager.downloadNotes(
////            noteName,
////            onSuccess = { localFile ->
////                // Use the safe call operator ?. to access the non-null context
////                context?.let {
////                    showNotification(
////                        it,
////                        "Notes downloaded",
////                        "File downloaded successfully"
////                    )
////                    openFile(it, localFile)
////                }
////            },
////            onFailure = { exception ->
////                // Use the safe call operator ?. to access the non-null context
////                context?.let {
////                    showNotification(
////                        it,
////                        "Error",
////                        "Failed to download notes"
////                    )
////                }
////                Log.e(TAG, "Failed to download notes: $exception")
////            }
////        )
////    }
//}
package com.jecrc.learning_edge

import android.Manifest
import android.content.ContentValues.TAG
import android.content.Context
import android.content.pm.PackageManager
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jecrc.learning_edge.NotificationUtils.showNotification
import com.jecrc.learning_edge.databinding.FragmentNotesBinding
import java.io.File

class NotesFragment : Fragment() {

    private lateinit var binding: FragmentNotesBinding
    private lateinit var notesRecyclerView: RecyclerView
    private lateinit var notesAdapter: NotesAdapter
    private lateinit var progressBar: ProgressBar

    private val STORAGE_PERMISSION_CODE = 1001
    private lateinit var notesDropdown: Spinner
    private val WRITE_EXTERNAL_STORAGE_REQUEST = 1001
    private val WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentNotesBinding.inflate(inflater, container, false)
        notesRecyclerView = binding.notesRecyclerView
        notesRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Initialize the progressBar
        progressBar = binding.progressBar
        progressBar.visibility = View.GONE

        // Set LayoutManager for the RecyclerView
        notesRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Retrieve the selected semester and branch from SharedPreferences
        val sharedPref = requireActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val selectedSemester = sharedPref.getString("selectedSemester", null)
        val selectedBranch = sharedPref.getString("selectedBranch", null)

        // Assuming you have a list of subjects with corresponding notes
        val subjectsWithNotes = mapOf(
            "Basic Civil Engineering (BCE)" to listOf("Common_BCE_Unit_5.pdf", "Common_BCE_unit_3_Part2.pdf"),
            "Engineering Chemistry" to listOf("Common_BCE_Unit_1&2.pdf", "Common_BCE_Unit_1&2.pdf")
            // Add more subjects with their corresponding notes
        )

        // Check if the selected semester is "1st Year" and selected branch is not null
        if (selectedSemester == "1st Year" && selectedBranch != null) {
            // Set up the expandable sections for each subject button
            for ((index, subject) in subjectsWithNotes.keys.withIndex()) {
                val subjectButton = LayoutInflater.from(requireContext())
                    .inflate(R.layout.subject_notes_expandable, binding.subjectContainer, false) as LinearLayout
                subjectButton.findViewById<Button>(R.id.btnSubject).text = subject
                setupExpandableSection(subjectButton, subjectsWithNotes[subject] ?: emptyList())
                binding.subjectContainer.addView(subjectButton)
            }

            // Default to the first subject in the map
            val firstSubject = subjectsWithNotes.keys.first()
            val selectedSubjectNotes = subjectsWithNotes[firstSubject] ?: emptyList()

            // Set up the RecyclerView with the list of notes for the selected subject
            notesAdapter = NotesAdapter(subjectsWithNotes) // Pass the whole map of subjectsWithNotes
            notesRecyclerView.adapter = notesAdapter
        }
        return binding.root
    }

    private fun setupExpandableSection(linearLayout: LinearLayout, notes: List<String>) {
        // Find views inside the expandable layout
        val btnSubject: Button = linearLayout.findViewById(R.id.btnSubject)
        val expandableSection: LinearLayout = linearLayout.findViewById(R.id.expandableSection)
        notesDropdown = linearLayout.findViewById(R.id.dropdownNotes)
        val ivDownloadIcon: ImageView = linearLayout.findViewById(R.id.ivDownloadIcon)

        // Set up the ArrayAdapter for the Spinner
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            notes
        )
        notesDropdown.adapter = adapter

        // Set a click listener for the download icon
        ivDownloadIcon.setOnClickListener {
            val selectedNote = notesDropdown.selectedItem as? String
            selectedNote?.let {
                requestStoragePermission() // Request the storage permission first
            }
        }

        // Set a click listener for the subject button to toggle the expandable layout
        btnSubject.setOnClickListener {
            if (expandableSection.visibility == View.VISIBLE) {
                expandableSection.visibility = View.GONE
            } else {
                expandableSection.visibility = View.VISIBLE
            }
        }
    }

    private fun requestStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    WRITE_EXTERNAL_STORAGE_REQUEST
                )
            } else {
                // Permission already granted, you can proceed with the download
                val selectedNote = notesDropdown.selectedItem as? String
                selectedNote?.let {
                    downloadAndShowNote(it)
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == WRITE_EXTERNAL_STORAGE_REQUEST) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed with the download
                val selectedNote = notesDropdown.selectedItem as? String
                selectedNote?.let {
                    downloadAndShowNote(it)
                }
            } else {
                // Permission denied, show a message to the user or handle it accordingly
                // For example, you can show a toast message
                Toast.makeText(
                    requireContext(),
                    "Storage permission denied. Cannot download the file.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun downloadAndShowNote(noteName: String) {
        // Show the ProgressBar when the download starts
        progressBar.visibility = View.VISIBLE

        val context = requireContext() // Use requireContext() to get the non-null context

        // Initialize the FirebaseManager with the correct context
        val firebaseManager = FirebaseManager(context)

        firebaseManager.downloadNotes(
            noteName,
            onSuccess = { localFile ->
                // Hide the ProgressBar when the download is complete
                progressBar.visibility = View.GONE

                // Notify the user that the download is successful
                showNotification(
                    context,
                    "Notes downloaded",
                    "File downloaded successfully",
                    FileProvider.getUriForFile(
                        context,
                        "com.jecrc.learning_edge.fileprovider",
                        localFile
                    )
                )

                // Use MediaScanner to add the file to the device's media library
                MediaScannerConnection.scanFile(
                    context,
                    arrayOf(localFile.absolutePath),
                    null
                ) { _, _ -> /* No need to do anything here */ }
            },
            onFailure = { exception ->
                // Hide the ProgressBar on failure
                progressBar.visibility = View.GONE
                showNotification(
                    context,
                    "Error",
                    "Failed to download notes",
                    null // Pass null as the file, since the download failed
                )
                Log.e(TAG, "Failed to download notes: $exception")
            },
            onProgress = { progress ->
                // Update the progress of the ProgressBar during the download
                progressBar.progress = progress
            }
        )
    }


}
