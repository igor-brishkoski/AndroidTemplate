package com.example.data.repos

import com.example.data.models.User
import com.example.data.source.local.UserLocalSource
import com.example.data.source.remote.UserRemoteSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface UserRepository {
    suspend fun getUser(id: Int): User?
    fun getUsers(): Flow<List<User>>
    suspend fun refreshUsers()
}

class UserRepositoryImpl @Inject constructor(
    private val userRemoteSource: UserRemoteSource,
    private val userLocalSource: UserLocalSource,
    private val dispatcher: CoroutineDispatcher,
) : UserRepository {
    override suspend fun getUser(id: Int): User? = withContext(dispatcher) {
        userLocalSource.getUser(id)
    }

    override fun getUsers(): Flow<List<User>> {
        return userLocalSource.getUsers()
    }

    override suspend fun refreshUsers() = withContext(dispatcher) {
        val users = userRemoteSource.getUsers()
        userLocalSource.insertUsers(users)
    }
}