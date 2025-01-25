package com.example.zaverecnyprojekt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.zaverecnyprojekt.model.Country
import com.example.zaverecnyprojekt.ui.theme.ZaverecnyProjektTheme
import com.example.zaverecnyprojekt.view.CountryDetailScreen
import com.example.zaverecnyprojekt.view.CountryListScreen
import com.example.zaverecnyprojekt.viewmodel.CountryViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZaverecnyProjektTheme {
                val navController = rememberNavController()
                val viewModel = CountryViewModel()
                val countries by viewModel.countries.collectAsState()

                AppNavigation(navController, countries)
            }
        }
    }
}

@Composable
fun AppNavigation(navController: NavHostController, countries: List<Country>) {
    NavHost(navController = navController, startDestination = "countryList") {
        composable("countryList") {
            CountryListScreen(countries) { selectedCountry ->
                navController.navigate("countryDetail/${selectedCountry.name.common}")
            }
        }
        composable("countryDetail/{countryName}") { backStackEntry ->
            val countryName = backStackEntry.arguments?.getString("countryName")
            val country = countries.find { it.name.common == countryName }
            country?.let { CountryDetailScreen(it) }
        }
    }
}
