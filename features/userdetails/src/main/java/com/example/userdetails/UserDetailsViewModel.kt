package com.example.userdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.models.User
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

sealed class UserDetailsState {
    object Loading : UserDetailsState()
    data class Content(val user: User) : UserDetailsState()
    data class Error(val message: String) : UserDetailsState()
}

@HiltViewModel(assistedFactory = UserDetailsViewModel.Factory::class)
class UserDetailsViewModel @AssistedInject constructor(
    private val getUserDetailsUseCase: GetUserDetailsUseCase,
    @Assisted private val id: Int,
) : ViewModel() {
    val _state: MutableStateFlow<UserDetailsState> =
        MutableStateFlow<UserDetailsState>(UserDetailsState.Loading)

    val state = _state

    init {
        viewModelScope.launch {
            delay(1000)
            try {
                val user = getUserDetailsUseCase(id)
                user
                    ?.let { _state.value = UserDetailsState.Content(it) }
                    ?: { _state.value = UserDetailsState.Error("User not found") }
            } catch (e: Exception) {
                _state.value = UserDetailsState.Error(e.message ?: "Unknown error")
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(id: Int): UserDetailsViewModel
    }
}