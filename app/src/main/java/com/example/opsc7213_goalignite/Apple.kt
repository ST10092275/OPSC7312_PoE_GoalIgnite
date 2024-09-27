package com.example.opsc7213_goalignite

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Apple : AppCompatActivity() {

    private lateinit var loginButton: Button
    private lateinit var appleEmail: EditText
    private lateinit var passwordApple: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apple)

        loginButton = findViewById(R.id.login_button)
        appleEmail = findViewById(R.id.apple_email)
        passwordApple = findViewById(R.id.password_apple)

        loginButton.setOnClickListener {
            val email = appleEmail.text.toString()
            val password = passwordApple.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
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
