package com.example.zaverecnyprojekt.utils

import android.content.Context

class PreferencesHelper(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)

    fun saveSelectedRegion(region: String) {
        sharedPreferences.edit().putString("selected_region", region).apply()
    }

    fun getSelectedRegion(): String {
        return sharedPreferences.getString("selected_region", "All") ?: "All"
    }
}