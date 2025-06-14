package com.example.data.models

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int,
    val username: String,
    val name: String,
    val email: String,
    val phone: String,
    val website: String,
    val company: Company,
    val address: Address,
)

@Serializable
data class Company(
    val name: String,
    val catchPhrase: String,
    val bs: String,
)

@Serializable
data class Address(
    val street: String,
    val suite: String,
    val city: String,
    val zipcode: String,
    val geo: Geo,
)

@Serializable
data class Geo(
    val lat: String,
    val lng: String,
)
