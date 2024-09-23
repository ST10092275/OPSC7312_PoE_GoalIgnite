package com.example.opsc7213_goalignite


import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ContactDetails : AppCompatActivity() {
    private lateinit var nameTextView: TextView
    private lateinit var phoneTextView: TextView
    private lateinit var emailTextView: TextView
    private lateinit var subjectTextView: TextView
    private lateinit var dbHelper: DbHelper
    private lateinit var goingArrow:ImageView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_contact_details)

        // Initialize TextViews
        goingArrow = findViewById(R.id.goingArrow)
        nameTextView = findViewById(R.id.nameTextView)
        phoneTextView = findViewById(R.id.phoneTextView)
        emailTextView = findViewById(R.id.emailTextView)
        subjectTextView = findViewById(R.id.subjectTextView)


        goingArrow.setOnClickListener {
            finish()  // Close the current activity and go back to the previous one
        }
        // Set up window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize dbHelper
        dbHelper = DbHelper(this)

        // Load contact by ID
        val contactId = intent.getStringExtra("contactId") ?: return
        loadDataById(contactId)
    }

    private fun loadDataById(id: String) {
        // Query to find data by ID
        val selectQuery = "SELECT * FROM ${Constants.TABLE_NAME} WHERE ${Constants.C_ID}=\"$id\""

        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            nameTextView.text = cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_NAME))
            phoneTextView.text = cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_PHONE))
            emailTextView.text = cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_EMAIL))
            subjectTextView.text = cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_SUBJECT))
        }
        cursor.close()
        db.close()
    }
}