package com.example.data.source.local

import com.example.data.models.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface UserLocalSource {
    suspend fun getUser(username: String): Flow<User>
}

class UserLocalSourceImpl @Inject constructor() : UserLocalSource {
    override suspend fun getUser(username: String): Flow<User> {
        TODO("Not yet implemented")
    }
}