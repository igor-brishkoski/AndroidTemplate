package com.example.userdetails

import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.models.User

@Composable
fun UserDetailsScreenContainer(viewModel: UserDetailsViewModel) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    UserDetailsScreen(state.value)
}

@Composable
fun UserDetailsScreen(state: UserDetailsState) {
    when (state) {
        is UserDetailsState.Content -> Text(state.user.name)
        is UserDetailsState.Error -> Text(state.message)
        UserDetailsState.Loading -> CircularProgressIndicator(
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
            modifier = Modifier.width(64.dp)
        )
    }
}

@Preview(showBackground = true, apiLevel = 35)
@Composable
fun UserDetailsScreenPreview() {
    UserDetailsScreen(UserDetailsState.Loading)
}

@Preview(showBackground = true, apiLevel = 35)
@Composable
fun UserDetailsScreenPreviewError() {
    UserDetailsScreen(UserDetailsState.Error("Error"))
}

@Preview(showBackground = true, apiLevel = 35)
@Composable
fun UserDetailsScreenPreviewContent() {
    UserDetailsScreen(
        UserDetailsState.Content(
            User(
                1,
                "name",
                "username",
                "email",
                "5556667777",
                "website"
            )
        )
    )
}