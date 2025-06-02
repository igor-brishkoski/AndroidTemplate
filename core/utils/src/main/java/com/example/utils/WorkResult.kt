package com.example.utils

sealed class WorkResult<out R> {
    data class Success<out T>(val data: T) : WorkResult<T>()
    data class Error(val exception: Exception) : WorkResult<Nothing>()
    object Loading : WorkResult<Nothing>()
}

fun <T> WorkResult<T>.successOr(fallback: T): T {
    return (this as? WorkResult.Success<T>)?.data ?: fallback
}
