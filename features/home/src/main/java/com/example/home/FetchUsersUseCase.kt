package com.example.home

import com.example.data.repos.UserRepository
import javax.inject.Inject

class FetchUsersUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke() {
        userRepository.refreshUsers()
    }
}