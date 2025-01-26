package com.example.zaverecnyprojekt.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zaverecnyprojekt.model.Country
import com.example.zaverecnyprojekt.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CountryViewModel : ViewModel() {


    private val _countries = MutableStateFlow<List<Country>>(emptyList())
    val countries: StateFlow<List<Country>> = _countries


    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        fetchCountries()
    }

    private fun fetchCountries() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _isLoading.value = true
                val countryList = RetrofitClient.apiService.getAllCountries()
                android.util.Log.d("API", "Fetched ${countryList.size} countries successfully")
                _countries.value = countryList
            } catch (e: Exception) {
                android.util.Log.e("API", "Error fetching countries: ${e.message}")
                e.printStackTrace()
                _countries.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

}
