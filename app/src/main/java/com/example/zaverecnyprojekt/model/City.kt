package com.example.zaverecnyprojekt.model

data class City(
    val name: String,
    val country: String,
    val latitude: Double,
    val longitude: Double,
    val population: Int,
    val is_capital: Boolean
)
