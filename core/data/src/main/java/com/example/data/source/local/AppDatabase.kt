package com.example.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.models.User
import com.example.data.source.local.dao.UserDao

@Database(
    entities = [User::class],
    version = 1,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}