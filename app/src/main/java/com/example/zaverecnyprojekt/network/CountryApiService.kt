package com.example.zaverecnyprojekt.network

import com.example.zaverecnyprojekt.model.Country
import retrofit2.http.GET

interface CountryApiService {
    @GET("all")
    suspend fun getAllCountries(): List<Country>
}