package com.example.home

import com.example.data.repos.UserRepository
import javax.inject.Inject

class LoadUsersUseCase @Inject constructor(
    private val repository: UserRepository,
) {
    operator fun invoke() = repository.getUsers()
}