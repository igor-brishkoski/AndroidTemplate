package com.example.data.source.local

import com.example.data.models.User
import com.example.data.source.local.dao.UserDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface UserLocalSource {
    suspend fun getUser(id: Int): User?
    fun getUsers(): Flow<List<User>>
    suspend fun insertUsers(users: List<User>)
}

class UserLocalSourceImpl @Inject constructor(
    private val userDao: UserDao,
) : UserLocalSource {
    override suspend fun getUser(id: Int): User? {
        return userDao.findById(id)
    }

    override fun getUsers(): Flow<List<User>> {
        return userDao.getAll()
    }

    override suspend fun insertUsers(users: List<User>) {
        userDao.insertAll(users)
    }
}
