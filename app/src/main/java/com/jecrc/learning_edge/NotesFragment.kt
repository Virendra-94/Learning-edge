package com.jecrc.learning_edge
import android.Manifest
import android.content.ContentValues.TAG
import android.content.Context
import android.content.pm.PackageManager
import android.media.MediaScannerConnection
import android.os.Build
import android.os.Bundle
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
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jecrc.learning_edge.NotificationUtils.showNotification
import com.jecrc.learning_edge.databinding.FragmentNotesBinding

class NotesFragment : Fragment() {

    private lateinit var binding: FragmentNotesBinding
    private lateinit var notesRecyclerView: RecyclerView

    private lateinit var progressBar: ProgressBar

    private val STORAGE_PERMISSION_CODE = 1001
    private lateinit var notesDropdown: Spinner
    private val WRITE_EXTERNAL_STORAGE_REQUEST = 1001
    private val WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE
    private lateinit var notesAdapter: NotesAdapter
    private var subjectsWithNotes: Map<String, List<String>> = emptyMap()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Show the title bar in the Home Fragment
        activity?.actionBar?.show()

        // Set the title for the title bar in the Home Fragment
        activity?.title = "Notes"



        // Initialize the RecyclerView adapter with an empty mutable map
        notesAdapter = NotesAdapter(mutableMapOf())
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

        // Get the subjects and notes based on the selected branch and semester
        val subjectsWithNotes = getSubjectsAndNotes(selectedBranch, selectedSemester)

        // Check if there are any subjects and notes for the selected branch and semester
        if (subjectsWithNotes.isNotEmpty()) {
            // Set up the expandable sections for each subject button
            for ((subject, notes) in subjectsWithNotes) {
                val subjectButton = LayoutInflater.from(requireContext())
                    .inflate(R.layout.subject_notes_expandable, binding.subjectContainer, false) as LinearLayout
                subjectButton.findViewById<Button>(R.id.btnSubject).text = subject
                setupExpandableSection(subjectButton, notes)
                binding.subjectContainer.addView(subjectButton)



            }




            // Default to the first subject in the map
            val firstSubject = subjectsWithNotes.keys.first()
            val selectedSubjectNotes = subjectsWithNotes[firstSubject] ?: emptyList()

            // Set up the RecyclerView with the list of notes for the selected subject
            notesAdapter = NotesAdapter(subjectsWithNotes)
            notesRecyclerView.adapter = notesAdapter
        }

        return binding.root
    }

    private fun getSubjectsAndNotes(branch: String?, semester: String?): Map<String, List<String>> {
        val subjectsWithNotes = mapOf(
            "CSE 1st Year" to mapOf(
                "Engineering Mathematics-I" to listOf("Subject1_Notes.pdf", "Subject1_Notes_2.pdf"),
                "Engineering Chemistry" to listOf("Subject2_Notes.pdf"),
                "Human Values" to listOf("Subject2_Notes.pdf"),
                "Programming for Problem Solving" to listOf("Subject2_Notes.pdf"),
                "Basic Civil Engineering" to listOf("Subject2_Notes.pdf"),
                "Engineering Mathematics-II" to listOf("Subject2_Notes.pdf"),
                "Engineering Physics" to listOf("Subject2_Notes.pdf"),
                "Communication Skills" to listOf("Subject2_Notes.pdf"),
                "Basic Mechanical Engineering" to listOf("Subject2_Notes.pdf"),
                "Basic Electrical Engineering" to listOf("Subject2_Notes.pdf")
            ),
            "CSE 3rd Sem" to mapOf(
                "Advanced Engineering Mathematics" to listOf("CS3_AEM_Complete.pdf"),
                "Managerial Economics and Financial Accounting" to listOf("CS3_MEFA_Unit1.pdf","CS3_MEFA_Unit2.pdf","CS3_MEFA_Unit3.pdf","CS3_MEFA_Unit4.pdf","CS3_MEFA_Unit5.pdf"),
                "Digital Electronics" to listOf("CS3_DE_Unit1.pdf","CS3_DE_Unit2.pdf","CS3_DE_Unit3.pdf","CS3_DE_Unit4.pdf","CS3_DE_Unit5.pdf"),
                "Data Structures and Algorithms" to listOf("CS3_DSA_Unit1.pdf","CS3_DSA_Unit2.pdf","CS3_DSA_Unit3.pdf","CS3_DSA_Unit4.pdf","CS3_DSA_Unit5.pdf"),
                "Object Oriented Programming" to listOf("CS3_OOP_Unit1.pdf","CS3_OOP_Unit2.pdf","CS3_OOP_Unit3.pdf","CS3_OOP_Unit4.pdf"),
                "Software Engineering" to listOf("CS3_SE_Unit1.pdf","CS3_SE_Unit2.pdf","CS3_SE_Unit3.pdf","CS3_SE_Unit4.pdf","CS3_SE_Unit5.pdf")
            ),
            "CSE 4rd Sem" to mapOf(
                "Discrete Mathematics Structure" to listOf("CS4_DMS_Unit1.pdf","CS4_DMS_Unit2.pdf","CS4_DMS_Unit3.pdf","CS4_DMS_Unit4.pdf","CS4_DMS_Unit5.pdf"),
                "Technical Communication" to listOf("CS4_TC_Unit1.pdf","CS4_TC_Unit2.pdf","CS4_TC_Unit3.pdf","CS4_TC_Unit4.pdf","CS4_TC_Unit5.pdf"),
                "Microprocessor & Interfaces" to listOf("CS4_MPI_Unit1.pdf","CS4_MPI_Unit2.pdf","CS4_MPI_Unit3.pdf","CS4_MPI_Unit4.pdf","CS4_MPI_Unit5.pdf"),
                "Database Management System" to listOf("CS4_DBMS_Unit1.pdf","CS4_DBMS_Unit2.pdf","CS4_DBMS_Unit3.pdf","CS4_DBMS_Unit4.pdf","CS4_DBMS_Unit5.pdf"),
                "Theory Of Computation" to listOf("CS4_TOC_Unit1.pdf","CS4_TOC_Unit2.pdf","CS4_TOC_Unit3.pdf","CS4_TOC_Unit4.pdf","CS4_TOC_Unit5.pdf"),
                "Data Communication and Computer Networks" to listOf("CS4_DCCN_Unit1.pdf","CS4_DCCN_Unit2.pdf","CS4_DCCN_Unit3.pdf","CS4_DCCN_Unit4.pdf","CS4_DCCN_Unit5.pdf")
            ),
            "CSE 5th Sem" to mapOf(
                "Analysis of Algorithms" to listOf("CS5_AOA_Unit1.pdf", "CS5_AOA_Unit2.pdf","CS5_AOA_Unit3.pdf","CS5_AOA_Unit4.pdf","CS5_AOA_Unit5.pdf"),
                "Compiler Design" to listOf("CS5_CD_Unit1.pdf","CS5_CD_Unit2.pdf","CS5_CD_Unit3.pdf","CS5_CD_Unit4.pdf","CS5_CD_Unit5.pdf","CS5_CD_Unit6.pdf"),
                "Computers Graphics and Multimedia" to listOf("CS5_CG_Unit1.pdf","CS5_CG_Unit2.pdf","CS5_CG_Unit3.pdf","CS5_CG_Unit4.pdf","CS5_CG_Unit5.pdf"),
                "Infromation Theory and Coding" to listOf("CS5_ITC_Unit1.pdf","CS5_ITC_Unit2.pdf","CS5_ITC_Unit3.pdf","CS5_ITC_Unit4.pdf","CS5_ITC_Unit5.pdf"),
                "Operating Systems" to listOf("CS5_OS_Unit1.pdf","CS5_OS_Unit2.pdf","CS5_OS_Unit3.pdf","CS5_OS_Unit4.pdf","CS5_OS_Unit5.pdf"),
                "Wireless Communication" to listOf("CS5_WC_Unit1.pdf","CS5_WC_Unit2.pdf","CS5_WC_Unit3.pdf","CS5_WC_Unit4.pdf","CS5_WC_Unit5.pdf"),
            )  ,
            "CSE 6th Sem" to mapOf(
                "Artificial Intelligence" to listOf("Subject7_Notes.pdf", "Subject7_Notes_2.pdf"),
                "Cloud Computing" to listOf("Subject8_Notes.pdf"),
                "Computer Architecture and Organization" to listOf("Subject8_Notes.pdf"),
                "Digital Image Processing" to listOf("Subject8_Notes.pdf"),
                "Distributed System" to listOf("Subject8_Notes.pdf"),
                "Information Security System" to listOf("Subject8_Notes.pdf"),
                "Machine Learning" to listOf("Subject8_Notes.pdf")
            ),
            "CSE 7th Sem" to mapOf(
                "Human Engineering and Safety" to listOf("Subject7_Notes.pdf", "Subject7_Notes_2.pdf"),
                "Internet Of Things" to listOf("Subject8_Notes.pdf"),
            ),
            "CSE 8th Sem" to mapOf(
                "Big Data and Analytics" to listOf("Subject7_Notes.pdf", "Subject7_Notes_2.pdf"),
                "Disaster Management" to listOf("Subject8_Notes.pdf"),
            ),
            // Add more branches and semesters with their corresponding subjects and notes
        )

        // Retrieve subjects and notes based on the selected branch and semester
        val key = "$branch $semester"
        return subjectsWithNotes[key] ?: emptyMap()
    }

    private fun setupExpandableSection(linearLayout: LinearLayout, notes: List<String>) {
        // Find views inside the expandable layout
        val btnSubject: Button = linearLayout.findViewById(R.id.btnSubject)
        btnSubject.setBackgroundResource(R.drawable.subject_btn_style) // Apply the custom style
        btnSubject.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        // Create layout parameters for the Button
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, // Width
            LinearLayout.LayoutParams.WRAP_CONTENT // Height
        )

// Set margins for the Button (left, top, right, bottom)
        layoutParams.setMargins(10, 0, 10, 16) // Adjust the margins as needed

// Set the layout parameters for the Button
        btnSubject.layoutParams = layoutParams


//        val btnSubject: ImageButton = linearLayout.findViewById(R.id.btnSubject) // Update this line
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

//New
//interface SubjectClickListener {
//    fun onSubjectClicked(subject: String)
//}
