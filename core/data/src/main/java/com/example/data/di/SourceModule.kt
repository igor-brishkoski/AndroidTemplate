package com.example.data.di

import com.example.data.source.local.UserLocalSource
import com.example.data.source.local.UserLocalSourceImpl
import com.example.data.source.remote.UserRemoteSource
import com.example.data.source.remote.UserRemoteSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SourceModule {
    @Binds
    abstract fun bindUserRemoteSource(impl: UserRemoteSourceImpl): UserRemoteSource

    @Binds
    abstract fun bindUserLocalSource(impl: UserLocalSourceImpl): UserLocalSource

}