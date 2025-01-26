package com.example.zaverecnyprojekt.model

data class Country(
    val name: Name,
    val capital: List<String>?,
    val region: String,
    val population: Int,
    val flags: Flags,
    val area: Double?,
    val languages: Map<String, String>?,
    val timezones: List<String>,
    val currencies: Map<String, CurrencyInfo>?,
    val maps: Map<String, String>?
)

data class Name(
    val common: String,
    val official: String
)

data class Flags(
    val png: String,
    val svg: String
)

data class CurrencyInfo(
    val name: String,
    val symbol: String
)