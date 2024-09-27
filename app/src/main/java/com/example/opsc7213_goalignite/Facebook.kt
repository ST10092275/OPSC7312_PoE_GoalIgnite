package com.example.opsc7213_goalignite

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Facebook : AppCompatActivity() {

    private lateinit var loginButton: Button
    private lateinit var facebookEmail: EditText
    private lateinit var passwordFacebook: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_facebook)

        loginButton = findViewById(R.id.login_button)
        facebookEmail = findViewById(R.id.apple_email)
        passwordFacebook = findViewById(R.id.password_apple)

        loginButton.setOnClickListener {
            val email = facebookEmail.text.toString()
            val password = passwordFacebook.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                // You can add your login logic here
                // For now, just show a success message
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
            }

            loginButton.setOnClickListener {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            }
        }
}
