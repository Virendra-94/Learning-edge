package com.jecrc.learning_edge
import android.Manifest
import android.content.ContentValues
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
import com.jecrc.learning_edge.databinding.FragmentPyqsBinding

class PyqsFragment : Fragment() {

    private lateinit var binding: FragmentPyqsBinding
    private lateinit var pyqsRecyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar

    private val STORAGE_PERMISSION_CODE = 1001
    private lateinit var pyqsDropdown: Spinner
    private val WRITE_EXTERNAL_STORAGE_REQUEST = 1001
    private val WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE
    private lateinit var pyqsAdapter: PyqsAdapter
    private var subjectsWithPyqs: Map<String, List<String>> = emptyMap()

    // Declare selectedSemester and selectedBranch as class-level properties
    private var selectedSemester: String? = null
    private var selectedBranch: String? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPyqsBinding.inflate(inflater, container, false)
        pyqsRecyclerView = binding.pyqsRecyclerView
        pyqsRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        progressBar = binding.progressBar
        progressBar.visibility = View.GONE

        // Retrieve the selected semester and branch from SharedPreferences
        val sharedPref = requireActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val selectedSemester = sharedPref.getString("selectedSemester", null)
        val selectedBranch = sharedPref.getString("selectedBranch", null)

        // Get the subjects and PYQs based on the selected branch and semester
        subjectsWithPyqs = getSubjectsAndPyqs(selectedBranch, selectedSemester)

        // Check if there are any subjects and PYQs for the selected branch and semester
        if (subjectsWithPyqs.isNotEmpty()) {
            // Set up the expandable sections for each subject button
            for ((subject, pyqs) in subjectsWithPyqs) {
                val subjectButton = LayoutInflater.from(requireContext())
                    .inflate(R.layout.subject_pyqs_expandable, binding.subjectContainer, false) as LinearLayout
                subjectButton.findViewById<Button>(R.id.btnSubject).text = subject
                setupExpandableSection(subjectButton, pyqs)
                binding.subjectContainer.addView(subjectButton)
            }

            // Default to the first subject in the map
            val firstSubject = subjectsWithPyqs.keys.first()
            val selectedSubjectPyqs = subjectsWithPyqs[firstSubject] ?: emptyList()

            // Set up the RecyclerView with the list of PYQs for the selected subject
            pyqsAdapter = PyqsAdapter(selectedSubjectPyqs)
            pyqsRecyclerView.adapter = pyqsAdapter
        }

        return binding.root
    }

    private fun getSubjectsAndPyqs(branch: String?, semester: String?): Map<String, List<String>> {
        // Replace this with your actual data fetching logic based on branch and semester
        // For example, you can fetch data from Firebase or a local database
        val subjectsWithPyqs = when ("$branch $semester") {
            "CSE 1st Year" -> mapOf(
                "Engineering Mathematics-I" to listOf("Mathematics_1_23.pdf", "Mathematics_1_22_May.pdf","Mathematics_1_22_july.pdf"),
                "Engineering Chemistry" to listOf("Chemistry_23.pdf","Chemistry_22_May.pdf","Chemistry_22_July.pdf"),
                "Human Values" to listOf("HV_22_May.pdf","HV_19.pdf"),
                "Programming for Problem Solving" to listOf("PPS_23.pdf","PPS_22_May.pdf","PPS_22_July.pdf"),
                "Basic Civil Engineering" to listOf("BCE_23.pdf","BCE_22.pdf","BCE_22_Second.pdf","BCE_21.pdf"),
                "Engineering Mathematics-II" to listOf("Mathematics_1_23.pdf", "Mathematics_1_22_May.pdf"),
                "Engineering Physics" to listOf("Physics_23.pdf","Physics_22_May.pdf","Physics_22_July.pdf"),
                "Communication Skills" to listOf("CSK_23.pdf","CSK_22_May.pdf","CSK_22_July.pdf"),
                "Basic Mechanical Engineering" to listOf("BME_22_May.pdf","BME_20.pdf","BME_19.pdf"),
                "Basic Electrical Engineering" to listOf("BEE_23.pdf","BEE_22.pdf","BEE_22_May.pdf","BEE_22_July.pdf")
            )
            "CSE 3rd Sem" -> mapOf(
                "Advanced Engineering Mathematics" to listOf("CS3_AEM_Complete.pdf"),
                "Managerial Economics and Financial Accounting" to listOf("CS3_MEFA_Unit1.pdf","CS3_MEFA_Unit2.pdf","CS3_MEFA_Unit3.pdf","CS3_MEFA_Unit4.pdf","CS3_MEFA_Unit5.pdf"),
                "Digital Electronics" to listOf("CS3_DE_Unit1.pdf","CS3_DE_Unit2.pdf","CS3_DE_Unit3.pdf","CS3_DE_Unit4.pdf","CS3_DE_Unit5.pdf"),
                "Data Structures and Algorithms" to listOf("CS3_DSA_Unit1.pdf","CS3_DSA_Unit2.pdf","CS3_DSA_Unit3.pdf","CS3_DSA_Unit4.pdf","CS3_DSA_Unit5.pdf"),
                "Object Oriented Programming" to listOf("CS3_OOP_Unit1.pdf","CS3_OOP_Unit2.pdf","CS3_OOP_Unit3.pdf","CS3_OOP_Unit4.pdf"),
                "Software Engineering" to listOf("CS3_SE_Unit1.pdf","CS3_SE_Unit2.pdf","CS3_SE_Unit3.pdf","CS3_SE_Unit4.pdf","CS3_SE_Unit5.pdf")
            )
            "CSE 4rd Sem" -> mapOf(
                "Discrete Mathematics Structure" to listOf("CS4_DMS_Unit1.pdf","CS4_DMS_Unit2.pdf","CS4_DMS_Unit3.pdf","CS4_DMS_Unit4.pdf","CS4_DMS_Unit5.pdf"),
                "Technical Communication" to listOf("CS4_TC_Unit1.pdf","CS4_TC_Unit2.pdf","CS4_TC_Unit3.pdf","CS4_TC_Unit4.pdf","CS4_TC_Unit5.pdf"),
                "Microprocessor & Interfaces" to listOf("CS4_MPI_Unit1.pdf","CS4_MPI_Unit2.pdf","CS4_MPI_Unit3.pdf","CS4_MPI_Unit4.pdf","CS4_MPI_Unit5.pdf"),
                "Database Management System" to listOf("CS4_DBMS_Unit1.pdf","CS4_DBMS_Unit2.pdf","CS4_DBMS_Unit3.pdf","CS4_DBMS_Unit4.pdf","CS4_DBMS_Unit5.pdf"),
                "Theory Of Computation" to listOf("CS4_TOC_Unit1.pdf","CS4_TOC_Unit2.pdf","CS4_TOC_Unit3.pdf","CS4_TOC_Unit4.pdf","CS4_TOC_Unit5.pdf"),
                "Data Communication and Computer Networks" to listOf("CS4_DCCN_Unit1.pdf","CS4_DCCN_Unit2.pdf","CS4_DCCN_Unit3.pdf","CS4_DCCN_Unit4.pdf","CS4_DCCN_Unit5.pdf")
            )
            "CSE 5th Sem" -> mapOf(
                "Analysis of Algorithms" to listOf("AOA_23.pdf", "AOA_22_March.pdf","AOA_21.pdf"),
                "Compiler Design" to listOf("CD_23.pdf","CD_22.pdf","CD_21.pdf"),
                "Computers Graphics and Multimedia" to listOf("CG_23.pdf","CG_22.pdf","CG_21.pdf"),
                "Infromation Theory and Coding" to listOf("Subject8_Notes.pdf"),
                "Operating Systems" to listOf("OS_23.pdf","OS_22.pdf","OS_21.pdf"),
                "Wireless Communication" to listOf("WC_19.pdf"),
            )
            "CSE 6th Sem" -> mapOf(
                "Artificial Intelligence" to listOf("Subject7_Notes.pdf", "Subject7_Notes_2.pdf"),
                "Cloud Computing" to listOf("Subject8_Notes.pdf"),
                "Computer Architecture and Organization" to listOf("Subject8_Notes.pdf"),
                "Digital Image Processing" to listOf("Subject8_Notes.pdf"),
                "Distributed System" to listOf("Subject8_Notes.pdf"),
                "Information Security System" to listOf("Subject8_Notes.pdf"),
                "Machine Learning" to listOf("Subject8_Notes.pdf")
            )
            "CSE 7th Sem" -> mapOf(
                "Human Engineering and Safety" to listOf("Subject7_Notes.pdf", "Subject7_Notes_2.pdf"),
                "Internet Of Things" to listOf("Subject8_Notes.pdf"),
            )
            "CSE 8th Sem" -> mapOf(
                "Big Data and Analytics" to listOf("Subject7_Notes.pdf", "Subject7_Notes_2.pdf"),
                "Disaster Management" to listOf("Subject8_Notes.pdf"),
            )
            else -> emptyMap() // Handle cases where branch and semester combination is not found
        }

        return subjectsWithPyqs
    }


    private fun setupExpandableSection(linearLayout: LinearLayout, pyqs: List<String>) {
        // Find views inside the expandable layout
        val btnSubject: Button = linearLayout.findViewById(R.id.btnSubject)
        val expandableSection: LinearLayout = linearLayout.findViewById(R.id.expandableSection)
        pyqsDropdown = linearLayout.findViewById(R.id.dropdownPyqs) // Rename to match your layout
        val ivDownloadIcon: ImageView = linearLayout.findViewById(R.id.ivDownloadIcon)

        // Set up the ArrayAdapter for the Spinner
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            pyqs
        )
        pyqsDropdown.adapter = adapter

        // Set a click listener for the download icon
        ivDownloadIcon.setOnClickListener {
            val selectedPyq = pyqsDropdown.selectedItem as? String
            selectedPyq?.let {
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
                val selectedPyq = pyqsDropdown.selectedItem as? String
                selectedPyq?.let {
                    downloadAndShowPyq(it,selectedBranch,selectedSemester)
                }
            }
        }
    }



    // Handle onRequestPermissionsResult as shown in the NotesFragment
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == WRITE_EXTERNAL_STORAGE_REQUEST) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed with the download
                val selectedPyq = pyqsDropdown.selectedItem as? String
                selectedPyq?.let {
                    downloadAndShowPyq(it,selectedBranch,selectedSemester)
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
    private fun downloadAndShowPyq(pyqName: String, branch: String?, semester: String?) {
        // Show the ProgressBar when the download starts
        progressBar.visibility = View.VISIBLE

        val context = requireContext() // Use requireContext() to get the non-null context

        // Initialize the FirebaseManager with the correct context
        val firebaseManager = FirebaseManager(context)

        firebaseManager.downloadPyqs(
            branch,
            semester,
            pyqName,
            onSuccess = { localFile ->
                // Hide the ProgressBar when the download is complete
                progressBar.visibility = View.GONE

                // Notify the user that the download is successful
                showNotification(
                    context,
                    "Pyq downloaded",
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
                    "Failed to download Pyq",
                    null // Pass null as the file, since the download failed
                )
                Log.e(ContentValues.TAG, "Failed to download Pyq: $exception")
            },
            onProgress = { progress ->
                // Update the progress of the ProgressBar during the download
                progressBar.progress = progress
            }
        )
    }

}