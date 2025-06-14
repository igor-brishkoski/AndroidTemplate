package com.example.baseapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.example.data.models.User
import com.example.data.repos.UserRepository
import com.example.network.Clients
import com.example.ui.theme.BaseAppTheme
import dagger.hilt.android.AndroidEntryPoint
import io.ktor.client.request.get
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject lateinit var userRepo: UserRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        Log.d("MainActivity", "onCreate: ${BuildConfig.BUILD_CONFIG_BASE_URL}")
        runBlocking {
            // Clients.ktorClient().get(BuildConfig.BUILD_CONFIG_BASE_URL+"users") {
            //     // Log.d("MainActivity", "onCreate: ${this.body}")
            //     // val users: List<User> = Json.decodeFromString(body)
            //     // Log.d("MainActivity", "onCreate: $users")
            // }
        }

        lifecycleScope.launch {
            val users = userRepo.getUsers()
            Log.d("MainActivity", "onCreate: ${users.first()}")
        }
        setContent {
            BaseAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BaseAppTheme {
        Greeting("Android")
    }
}