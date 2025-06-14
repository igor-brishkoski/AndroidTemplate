package com.example.data.di

import android.content.Context
import androidx.room.Room
import com.example.data.source.local.AppDatabase
import com.example.data.source.local.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object DbModule {
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "database-name",
        ).build()

    @Provides
    fun provideUserDao(db: AppDatabase): UserDao = db.userDao()

    // move this
    @Provides
    fun provideDispatcher(): CoroutineDispatcher = Dispatchers.IO
}