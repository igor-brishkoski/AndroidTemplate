package com.example.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.models.User
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

sealed class HomeState() {
    object Loading : HomeState()
    object Empty : HomeState()
    data class Content(val users: List<User>) : HomeState()
    data class Error(val message: String) : HomeState()
}

@HiltViewModel(assistedFactory = HomeViewModel.Factory::class)
class HomeViewModel @AssistedInject constructor(
    private val loadUsersUseCase: LoadUsersUseCase,
    private val fetchUsersUseCase: FetchUsersUseCase,
) : ViewModel() {
    private val _state: MutableStateFlow<HomeState> = MutableStateFlow(HomeState.Loading)
    val state = _state

    init {
        viewModelScope.launch {
            delay(1000)
            loadUsersUseCase().collect {
                if (it.isEmpty()) {
                    fetchUsersUseCase()
                } else {
                    _state.value = HomeState.Content(it)
                }
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(): HomeViewModel
    }
}
