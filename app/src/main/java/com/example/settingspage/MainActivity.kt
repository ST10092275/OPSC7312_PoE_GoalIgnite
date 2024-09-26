package com.example.settingspage

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import java.util.regex.Pattern
import com.example.settingspage.ui.theme.SettingsPageTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SettingsPageTheme {
                // A surface container using the 'background' color from the theme
                AppNavigation()

            }
        }
    }
}

// Set up navigation between Settings Menu and Support Page
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "settings_menu") {
        composable("settings_menu") { SettingsMenu(navController = navController, context = LocalContext.current) }
        composable("support_page") { SupportPage() }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SettingsMenu(navController: NavHostController, context: Context) {
    // State variables for profile fields
    var name by remember { mutableStateOf(TextFieldValue("")) }
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    var passwordError by remember { mutableStateOf(false) }
    var selectedTheme by remember { mutableStateOf("Light") }
    var selectedLanguage by remember { mutableStateOf("English") }

    // Load stored data from EncryptedSharedPreferences when the screen is initialized
    LaunchedEffect(Unit) {
        val storedData = loadProfileData(context)
        name = TextFieldValue(storedData.first ?: "")
        email = TextFieldValue(storedData.second ?: "")
        password = TextFieldValue(storedData.third ?: "")
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Settings") })
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Start
        ) {
            // Profile Settings Section
            Text(text = "Profile Settings", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))

            // Name Field
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Email Field
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Password Field with validation
            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    passwordError = !isValidPassword(it.text)
                },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                isError = passwordError
            )
            if (passwordError) {
                Text(
                    text = "Password must be at least 8 characters long and contain at least one number and one letter.",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.displayMedium
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Theme Selection
            Text(text = "Theme", style = MaterialTheme.typography.titleLarge)
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = selectedTheme == "Light",
                    onClick = { selectedTheme = "Light" }
                )
                Text(text = "Light")
                Spacer(modifier = Modifier.width(16.dp))
                RadioButton(
                    selected = selectedTheme == "Dark",
                    onClick = { selectedTheme = "Dark" }
                )
                Text(text = "Dark")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Language Selection
            Text(text = "Language", style = MaterialTheme.typography.titleLarge)
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = selectedLanguage == "English",
                    onClick = { selectedLanguage = "English" }
                )
                Text(text = "English")
                Spacer(modifier = Modifier.width(16.dp))
                RadioButton(
                    selected = selectedLanguage == "Zulu",
                    onClick = { selectedLanguage = "Zulu" }
                )
                Text(text = "Zulu")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Save Button with validation
            Button(onClick = {
                if (isValidPassword(password.text)) {
                    saveProfileData(context, name.text, email.text, password.text)
                    Toast.makeText(context, "Profile saved successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Invalid password", Toast.LENGTH_SHORT).show()
                }
            }) {
                Text("Save Profile")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Navigate to Support Page
            Text(
                text = "Support",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .clickable { navController.navigate("support_page") }
                    .padding(vertical = 8.dp)
            )
        }
    }
}

// Function to validate the password
fun isValidPassword(password: String): Boolean {
    val passwordPattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")
    return passwordPattern.matcher(password).matches()
}

// Function to securely save profile data
fun saveProfileData(context: Context, name: String, email: String, password: String) {
    val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    val encryptedSharedPreferences = EncryptedSharedPreferences.create(
        context,
        "secure_prefs",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    with(encryptedSharedPreferences.edit()) {
        putString("name", name)
        putString("email", email)
        putString("password", password)
        apply()
    }
}

// Function to load profile data from EncryptedSharedPreferences
fun loadProfileData(context: Context): Triple<String?, String?, String?> {
    val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    val encryptedSharedPreferences = EncryptedSharedPreferences.create(
        context,
        "secure_prefs",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    val name = encryptedSharedPreferences.getString("name", null)
    val email = encryptedSharedPreferences.getString("email", null)
    val password = encryptedSharedPreferences.getString("password", null)

    return Triple(name, email, password)
}

// Support Page with FAQs
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SupportPage() {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Support") })
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Start
        ) {
            Text(text = "Frequently Asked Questions", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "1. How do I reset my password?")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "2. How do I change my email?")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "3. How do I contact support?")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSettingsMenu() {
    SettingsPageTheme {
        val context = LocalContext.current  // Use local context
        val navController = rememberNavController()
        SettingsMenu(navController = navController, context = context)
    }
}