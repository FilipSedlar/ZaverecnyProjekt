package com.example.zaverecnyprojekt.model

data class Country(
    val name: Name,
    val capital: List<String>?, // Některé země nemají hlavní město
    val region: String,
    val population: Int,
    val flags: Flags,
    val area: Double?,
    val languages: Map<String, String>? // Klíč je kód jazyka (např. "cs" pro češtinu)
)

data class Name(
    val common: String,
    val official: String
)

data class Flags(
    val png: String,
    val svg: String
)