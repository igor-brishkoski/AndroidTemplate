package com.example.data.source.remote

import com.example.data.models.User
import com.example.network.di.NoLoggingOkHttp
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

interface UserRemoteSource {
    suspend fun getUsers(): List<User>
}

class UserRemoteSourceImpl @Inject constructor(
    @NoLoggingOkHttp private val ktorClient: HttpClient,
) : UserRemoteSource {
    override suspend fun getUsers(): List<User> {
        return ktorClient.get("https://jsonplaceholder.typicode.com/users").body()
    }
}