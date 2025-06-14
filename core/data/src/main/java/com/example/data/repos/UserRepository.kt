package com.example.data.repos

import com.example.data.models.Address
import com.example.data.models.Company
import com.example.data.models.Geo
import com.example.data.models.User
import com.example.data.source.local.UserLocalSource
import com.example.data.source.remote.UserRemoteSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

interface UserRepository {
    fun getUser(username: String): Flow<User>
    suspend fun getUsers(): List<User>
    suspend fun refreshUser(username: String)
}

class UserRepositoryImpl @Inject constructor(
    private val userRemoteSource: UserRemoteSource,
    private val userLocalSource: UserLocalSource,
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
                company = Company(
                    name = "test",
                    catchPhrase = "test",
                    bs = "test",
                ),
                address = Address(
                    street = "test",
                    suite = "test",
                    city = "test",
                    zipcode = "test",
                    geo = Geo(
                        lat = "test",
                        lng = "test",
                    ),
                )
            )
        )
    }

    override suspend fun getUsers(): List<User> {
        return userRemoteSource.getUsers()
    }

    override suspend fun refreshUser(username: String) {
        TODO("Not yet implemented")
    }
}