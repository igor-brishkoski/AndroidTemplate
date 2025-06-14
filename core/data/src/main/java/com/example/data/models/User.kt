package com.example.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity
data class User(
    @PrimaryKey val id: Int,
    val username: String,
    val name: String,
    val email: String,
    val phone: String,
    val website: String,
)

