package com.example.data.source.remote

import javax.inject.Inject

interface UserRemoteSource {
    suspend fun refreshUser(username: String)
}

class UserRemoteSourceImpl @Inject constructor(): UserRemoteSource {
    override suspend fun refreshUser(username: String) {
        TODO("Not yet implemented")
    }
}