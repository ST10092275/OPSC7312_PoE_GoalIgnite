package com.example.opsc7213_goalignite

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class AddEditContact : AppCompatActivity() {

    // Declare your variables
    private lateinit var profile: ImageView
    private lateinit var nameTeacher: EditText
    private lateinit var phoneTeacher: EditText
    private lateinit var emailTeacher: EditText
    private lateinit var subjectTeacher: EditText
    private lateinit var newContactButton: Button

    // String variables to hold user input
    private lateinit var name: String
    private lateinit var phone: String
    private lateinit var email: String
    private lateinit var subject: String



    // Database helper
    private lateinit var dbHelper: DbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_contact)


        // Initialize the back arrow
        val backArrow: ImageView = findViewById(R.id.backArrow)
        backArrow.setOnClickListener {
            // Navigate back to the previous activity
            finish() // This will close the current activity and return to the previous one
        }


        // Initialize dbHelper
        dbHelper = DbHelper(this)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize the views
        profile = findViewById(R.id.profile)
        nameTeacher = findViewById(R.id.nameTeacher)
        phoneTeacher = findViewById(R.id.phoneTeacher)
        emailTeacher = findViewById(R.id.emailTeacher)
        subjectTeacher = findViewById(R.id.subjectTeacher)
        newContactButton = findViewById(R.id.newContactButton)

        // Add event handler for the button click
        newContactButton.setOnClickListener {
            saveData()
        }
    }

    // Function to save the data
    private fun saveData() {
        name = nameTeacher.text.toString()
        phone = phoneTeacher.text.toString()
        email = emailTeacher.text.toString()
        subject = subjectTeacher.text.toString()

        // Check if all fields are empty or not
        if (name.isNotEmpty() || phone.isNotEmpty() || email.isNotEmpty() || subject.isNotEmpty()) {
            // Save data to the database
            val id = dbHelper.insertContact(
                name = name,
                phone = phone,
                email = email,
                subject = subject
            )

            // Show a toast message to confirm insertion
            Toast.makeText(applicationContext, "Inserted: $id", Toast.LENGTH_SHORT).show()

        } else {
            // Show toast message if nothing is entered
            Toast.makeText(applicationContext, "Nothing to save..", Toast.LENGTH_SHORT).show()
        }
    }

    // Override the support navigate up method to handle the back button
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
