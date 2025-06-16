package com.example.userdetails

import com.example.data.models.User
import com.example.data.repos.UserRepository
import javax.inject.Inject

class GetUserDetailsUseCase @Inject constructor(
    private val repository: UserRepository,
) {
    suspend operator fun invoke(id: Int): User? =
        repository.getUser(id)
}