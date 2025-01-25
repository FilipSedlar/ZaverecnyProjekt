package com.example.zaverecnyprojekt.network

import com.example.zaverecnyprojekt.model.Coutnry
import retrofit2.http.GET
import retrofit2.http.Query

interface CountryApiService {
    @GET("city")
    suspend fun getCities(
        @Query("name") name: String
    ): List<Coutnry>
}