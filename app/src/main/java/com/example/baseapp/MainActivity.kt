package com.example.baseapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.ui.rememberSceneSetupNavEntryDecorator
import com.example.home.HomeScreenContainer
import com.example.home.HomeViewModel
import com.example.userdetails.UserDetailsScreenContainer
import com.example.userdetails.UserDetailsViewModel
import com.example.ui.theme.BaseAppTheme
import dagger.hilt.android.AndroidEntryPoint

private data object Home
private data class UserDetails(val id: Int)

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        Log.d("MainActivity", "onCreate: ${BuildConfig.BUILD_CONFIG_BASE_URL}")

        setContent {
            val backStack = remember { mutableStateListOf<Any>(Home) }

            BaseAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavDisplay(
                        backStack = backStack,
                        onBack = { backStack.removeAt(backStack.lastIndex) },
                        entryDecorators = listOf(
                            rememberSceneSetupNavEntryDecorator(),
                            rememberSavedStateNavEntryDecorator(),
                            rememberViewModelStoreNavEntryDecorator()
                        ),
                        modifier = Modifier.padding(innerPadding),
                        entryProvider = entryProvider {
                            entry<Home> {
                                val viewModel = hiltViewModel<HomeViewModel, HomeViewModel.Factory>(
                                    creationCallback = { factory ->
                                        factory.create()
                                    }
                                )
                                HomeScreenContainer(viewModel, onUserClick = { id ->
                                    backStack.add(UserDetails(id))
                                })
                            }

                            entry<UserDetails> { key ->
                                val viewModel =
                                    hiltViewModel<UserDetailsViewModel, UserDetailsViewModel.Factory>(
                                        creationCallback = { factory ->
                                            factory.create(key.id)
                                        }
                                    )
                                UserDetailsScreenContainer(viewModel)
                            }
                        }
                    )
                }
            }
        }
    }
}
