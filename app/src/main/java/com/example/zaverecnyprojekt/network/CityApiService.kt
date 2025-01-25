package com.example.zaverecnyprojekt.network

import com.example.zaverecnyprojekt.model.City
import retrofit2.http.GET
import retrofit2.http.Query

interface CityApiService {
    @GET("city")
    suspend fun getCities(
        @Query("name") name: String
    ): List<City>
}