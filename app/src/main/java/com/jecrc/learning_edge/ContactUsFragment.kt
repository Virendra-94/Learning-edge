package com.jecrc.learning_edge

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.jecrc.learning_edge.databinding.FragmentContactUsBinding



class ContactUsFragment : Fragment() {

    private lateinit var binding: FragmentContactUsBinding
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactUsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        database = FirebaseDatabase.getInstance().getReference("/User")

        binding.btnSend.setOnClickListener {
            val name = binding.editTextName.text.toString().trim()
            val email = binding.editTextEmail.text.toString().trim()
            val message = binding.editTextMessage.text.toString().trim()

            if (name.isEmpty() || email.isEmpty() || message.isEmpty()) {
                // Show error Snackbar if any field is empty
                showSnackbar("Please fill in all details")
                return@setOnClickListener
            }

            if (!isValidEmail(email)) {
                // Show error Snackbar if email format is invalid
                showSnackbar("Invalid email format")
                return@setOnClickListener
            }

            // Send the message to Firebase database
            val contactId = database.push().key
            val contact = ContactForm(contactId, name, email, message)
            if (contactId != null) {
                database.child(contactId).setValue(contact)
                showSnackbar("Message sent successfully")
                clearFields()
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        // You can use a regular expression for email validation
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return email.matches(emailPattern.toRegex())
    }

    private fun showSnackbar(message: String) {
        val snackbar = Snackbar.make(
            requireView().findViewById(R.id.snackbar_container),
            message,
            Snackbar.LENGTH_SHORT
        )

        // Create a custom behavior to show the Snackbar at the top
        val params = snackbar.view.layoutParams as CoordinatorLayout.LayoutParams
        params.gravity = Gravity.TOP
        snackbar.view.layoutParams = params

        snackbar.show()
    }

    private fun clearFields() {
        binding.editTextName.text.clear()
        binding.editTextEmail.text.clear()
        binding.editTextMessage.text.clear()
    }
}