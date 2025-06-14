package com.example.data.repos

import com.example.data.models.User
import com.example.data.source.local.UserLocalSource
import com.example.data.source.remote.UserRemoteSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

interface UserRepository {
    fun getUser(username: String): Flow<User>
    suspend fun refreshUser(username: String)
}

class UserRepositoryImpl @Inject constructor(
    private val userRemoteSource: UserRemoteSource,
    private val userLocalSource: UserLocalSource,
) : UserRepository {
    override fun getUser(username: String): Flow<User> {
        return flowOf(User("test"))
    }

    override suspend fun refreshUser(username: String) {
        TODO("Not yet implemented")
    }
}