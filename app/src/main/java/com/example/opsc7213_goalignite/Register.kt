package com.example.opsc7213_goalignite

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Register : AppCompatActivity() {

    private lateinit var registerButton: Button
    private lateinit var facebookButton: ImageButton
    private lateinit var googleButton: ImageButton
    private lateinit var appleButton: ImageButton
    private lateinit var loginButton: Button
    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        registerButton = findViewById(R.id.register_but)
        facebookButton = findViewById(R.id.facebook_button)
        googleButton = findViewById(R.id.google_button)
        appleButton = findViewById(R.id.apple_button)
        loginButton = findViewById(R.id.login_button)
        nameEditText = findViewById(R.id.names_edittext)
        emailEditText = findViewById(R.id.emails_edittext)
        passwordEditText = findViewById(R.id.passwords_edittext)
        confirmPasswordEditText = findViewById(R.id.confirm_passwords_edittext)

        registerButton.setOnClickListener {
            if (validateInput()) {
                Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()
            }
        }

        facebookButton.setOnClickListener {
            val intent = Intent(this, Google::class.java)
            startActivity(intent)
        }

        googleButton.setOnClickListener {
            val intent = Intent(this, Facebook::class.java)
            startActivity(intent)
        }

        appleButton.setOnClickListener {
            val intent = Intent(this, Apple::class.java)
            startActivity(intent)
        }

        loginButton.setOnClickListener {
            val intent = Intent(this, MainActivity1::class.java)
            startActivity(intent)
        }
    }

    private fun validateInput(): Boolean {
        val name = nameEditText.text.toString()
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()
        val confirmPassword = confirmPasswordEditText.text.toString()

        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return false
        }

        if (password != confirmPassword) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
        }
}
