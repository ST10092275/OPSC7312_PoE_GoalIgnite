package com.example.opsc7213_goalignite

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class MainActivity1 : AppCompatActivity() {

    private lateinit var forgotButton: Button
    private lateinit var loginButton: Button
    private lateinit var facebookButton: ImageButton
    private lateinit var googleButton: ImageButton
    private lateinit var appleButton: ImageButton
    private lateinit var registerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main1)

        forgotButton = findViewById(R.id.forgot_button)
        loginButton = findViewById(R.id.login_button)
        facebookButton = findViewById(R.id.facebook_button)
        googleButton = findViewById(R.id.google_button)
        appleButton = findViewById(R.id.apple_button)
        registerButton = findViewById(R.id.register_button)

        forgotButton.setOnClickListener {
            val intent = Intent(this, Password::class.java)
            startActivity(intent)
        }

        loginButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
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

        registerButton.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
            }
        }
}
