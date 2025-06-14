package com.example.network.di

import com.example.network.Clients
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NoLoggingOkHttp

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LoggingOkHttp

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @NoLoggingOkHttp
    @Provides
    fun bindNoLoggingOkHttp(): HttpClient =
        Clients.ktorClientNoLogging()

    @LoggingOkHttp
    @Provides
    fun bindLoggingOkHttp(): HttpClient =
        Clients.ktorClientLogging()
}