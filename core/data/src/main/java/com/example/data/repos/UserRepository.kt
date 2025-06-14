package com.example.data.repos


import com.example.data.models.User
import com.example.data.source.local.UserLocalSource
import com.example.data.source.remote.UserRemoteSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface UserRepository {
    fun getUser(username: String): Flow<User>
    fun getUsers(): Flow<List<User>>
    suspend fun refreshUsers()
}

class UserRepositoryImpl @Inject constructor(
    private val userRemoteSource: UserRemoteSource,
    private val userLocalSource: UserLocalSource,
    private val dispatcher: CoroutineDispatcher,
) : UserRepository {
    override fun getUser(username: String): Flow<User> {
        return flowOf(
            User(
                id = 1,
                username = "test",
                name = "test",
                email = "test",
                phone = "test",
                website = "test",
            )
        )
    }

    override fun getUsers(): Flow<List<User>> {
        return userLocalSource.getUsers()
    }

    override suspend fun refreshUsers() = withContext(dispatcher) {
        val users = userRemoteSource.getUsers()
        userLocalSource.insertUsers(users)
    }
}