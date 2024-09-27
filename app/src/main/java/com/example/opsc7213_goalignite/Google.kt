package com.example.opsc7213_goalignite

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Google : AppCompatActivity() {

    private lateinit var loginButton: Button
    private lateinit var googleEmail: EditText
    private lateinit var passwordGoogle: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google)

        loginButton = findViewById(R.id.login_button)
        googleEmail = findViewById(R.id.apple_email)
        passwordGoogle = findViewById(R.id.password_apple)

        loginButton.setOnClickListener {
            val email = googleEmail.text.toString()
            val password = passwordGoogle.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {

                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
            }
            }

        loginButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        }
}
