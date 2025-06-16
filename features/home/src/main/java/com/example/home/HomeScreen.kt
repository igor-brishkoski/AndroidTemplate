package com.example.home

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.models.User

@Composable
fun HomeScreenContainer(viewModel: HomeViewModel, onUserClick: (Int) -> Unit) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    Log.d("HomeScreenContainer", "HomeScreenContainer: init... ")
    HomeScreen(state.value, onUserClick)
}

@Composable
fun HomeScreen(state: HomeState, onUserClick: (Int) -> Unit) {
    when (state) {
        is HomeState.Content -> {
            Column(
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                for (user in state.users) {
                    UserCard(
                        user.id,
                        user.name,
                        user.username,
                        user.email,
                        user.website,
                        onUserClick,
                    )
                }
            }
        }

        HomeState.Empty -> Text("Empty")
        is HomeState.Error -> Text(state.message)
        HomeState.Loading -> {
            Log.d("HomeScreen", "HomeScreen: Loading")
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize(),
            ) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.secondary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                    modifier = Modifier.width(64.dp)
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true, apiLevel = 35)
fun HomeScreenPreviewEmpty() {
    HomeScreen(HomeState.Empty) {}
}

@Composable
@Preview(showBackground = true, apiLevel = 35)
fun HomeScreenPreviewContent() {
    HomeScreen(
        HomeState.Content(
            listOf(
                User(1, "name", "username", "email", "5556667777", "website"),
                User(2, "name", "username", "email", "5556667777", "website"),
            )
        )
    ) {}
}

@Composable
fun UserCard(
    id: Int,
    name: String,
    username: String,
    email: String,
    website: String,
    onUserClick: (Int) -> Unit,
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(text = name, style = MaterialTheme.typography.titleLarge)
            Text(text = "@$username")
            Text(text = email, color = MaterialTheme.colorScheme.primary)
            Text(text = "https://www.$website")

            Button(
                onClick = { onUserClick(id) },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(text = "View User Details")
            }
        }
    }
}

@Preview(showBackground = true, apiLevel = 35)
@Composable
fun UserCardPreview() {
    UserCard(
        1,
        "name",
        "username",
        "email",
        "website"
    ) {}
}