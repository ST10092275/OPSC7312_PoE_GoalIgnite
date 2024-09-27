package com.example.settingsapp

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
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.settingsapp.ui.theme.SettingsAppTheme
import java.util.regex.Pattern
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.currentBackStackEntryAsState




class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SettingsAppTheme {
                AppNavigation()
            }



        }
    }
}







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
    var name by remember { mutableStateOf(TextFieldValue("")) }
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    var passwordError by remember { mutableStateOf(false) }
    var selectedTheme by remember { mutableStateOf("Light") }
    var selectedLanguage by remember { mutableStateOf("English") }

    // Load stored profile data from EncryptedSharedPreferences
    LaunchedEffect(Unit) {
        val storedData = loadProfileData(context)
        name = TextFieldValue(storedData.first ?: "")
        email = TextFieldValue(storedData.second ?: "")
        password = TextFieldValue(storedData.third ?: "")
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                backgroundColor = MaterialTheme.colorScheme.primary
            )
        },
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            // Profile Management Section
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { /* Navigate to Profile Management Screen */ }
                    .padding(vertical = 16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.round_account_circle_24), // Add profile icon
                    contentDescription = "Profile Icon",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = "Profile Management", fontWeight = FontWeight.Bold)
            }

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

            // Password Field with Validation
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
                    style = MaterialTheme.typography.displaySmall
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Theme Selection Section
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { /* Handle theme selection */ }
                    .padding(vertical = 16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_sunny_24), // Add theme icon
                    contentDescription = "Theme Icon",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = "Theme", fontWeight = FontWeight.Bold)
            }

            // Theme Selection
            Column(Modifier.selectableGroup()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = selectedTheme == "Light",
                            onClick = { selectedTheme = "Light" }
                        )
                        .padding(8.dp)
                ) {
                    RadioButton(
                        selected = selectedTheme == "Light",
                        onClick = { selectedTheme = "Light" }
                    )
                    Text(text = "Light mode")
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = selectedTheme == "Dark",
                            onClick = { selectedTheme = "Dark" }
                        )
                        .padding(8.dp)
                ) {
                    RadioButton(
                        selected = selectedTheme == "Dark",
                        onClick = { selectedTheme = "Dark" }
                    )
                    Text(text = "Dark mode")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Support Section
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { navController.navigate("support_page") }
                    .padding(vertical = 16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_mode_comment_24), // Add support icon
                    contentDescription = "Support Icon",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = "Support", fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Save Button with Profile Data Validation
            Button(
                onClick = {
                    if (isValidPassword(password.text)) {
                        saveProfileData(context, name.text, email.text, password.text)
                        Toast.makeText(context, "Profile saved successfully", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Invalid password", Toast.LENGTH_SHORT).show()
                    }
                }
            ) {
                Text("Save Profile")
            }
        }
    }
}

// Password Validation Function
fun isValidPassword(password: String): Boolean {
    val passwordPattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")
    return passwordPattern.matcher(password).matches()
}

// Securely Save Profile Data Using EncryptedSharedPreferences
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

// Load Profile Data from EncryptedSharedPreferences
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

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SupportPage() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Support") },
                backgroundColor = MaterialTheme.colorScheme.primary
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = "How can we help you?", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Frequently Asked Questions")
            Spacer(modifier = Modifier.height(8.dp))
            // Add more support content here
        }
    }
}
// Bottom Navigation Component
@Composable
fun BottomNavigationBar(navController: NavHostController) {
    BottomNavigation(
        backgroundColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.primary
    ) {
        // Home Icon
        BottomNavigationItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_home_24), //  home icon
                    contentDescription = "Home"
                )
            },
            label = { Text("Home") },
            selected = navController.currentBackStackEntryAsState().value?.destination?.route == "home",
            onClick = {
                navController.navigate("home") {
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )

        // Settings Icon
        BottomNavigationItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_display_settings_24), // settings icon
                    contentDescription = "Settings"
                )
            },
            label = { Text("Settings") },
            selected = navController.currentBackStackEntryAsState().value?.destination?.route == "settings",
            onClick = {
                navController.navigate("settings") {
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )

        // Time Icon
        BottomNavigationItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_access_time_24), // time icon
                    contentDescription = "Time"
                )
            },
            label = { Text("Time") },
            selected = navController.currentBackStackEntryAsState().value?.destination?.route == "time",
            onClick = {
                navController.navigate("time") {
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )

        // Calendar Icon
        BottomNavigationItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.outline_calendar_month_24), // calendar icon
                    contentDescription = "Calendar"
                )
            },
            label = { Text("Calendar") },
            selected = navController.currentBackStackEntryAsState().value?.destination?.route == "calendar",
            onClick = {
                navController.navigate("calendar") {
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
    }
}

// Navigation host to manage different pages
@Composable
fun NavigationHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "settings") {
        composable("home") {
            HomeScreen()
        }
        composable("settings") {
            SettingsPage()
        }
        composable("time") {
            TimeScreen()
        }
        composable("calendar") {
            CalendarScreen()
        }
    }
}

// Placeholder for Home Screen
@Composable
fun HomeScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Home Screen", fontSize = 24.sp)
    }
}

// Placeholder for Settings Page
@Composable
fun SettingsPage() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        // Profile Management
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.round_account_circle_24), // profile icon
                contentDescription = "Profile Management"
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = "Profile Management", fontSize = 18.sp)
        }

        Divider()

        // Theme Selection
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_sunny_24), // theme icon
                contentDescription = "Theme"
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = "Theme", fontSize = 18.sp)
        }

        Divider()

        // Support Section
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_mode_comment_24), // Replace with actual support icon
                contentDescription = "Support"
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = "Support", fontSize = 18.sp)
        }
    }
}

// Placeholder for Time Screen
@Composable
fun TimeScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Time Screen", fontSize = 24.sp)
    }
}

// Placeholder for Calendar Screen
@Composable
fun CalendarScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Calendar Screen", fontSize = 24.sp)
    }
}
