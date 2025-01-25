package com.example.zaverecnyprojekt

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
                AppNavigation(navController)
            }
        }
    }
}

@Composable
fun AppNavigation(navController: NavHostController) {
    val viewModel: CountryViewModel = androidx.lifecycle.viewmodel.compose.viewModel() // Používáme Jetpack Compose ViewModel
    val countries by viewModel.countries.collectAsState()

    NavHost(navController = navController, startDestination = "countryList") {
        composable("countryList") {
            CountryListScreen(viewModel) { selectedCountry ->
                navController.navigate("countryDetail/${selectedCountry.name.common}")
            }
        }
        composable("countryDetail/{countryName}") { backStackEntry ->
            val countryName = backStackEntry.arguments?.getString("countryName")
            val country = countries.find { it.name.common == countryName }
            country?.let { CountryDetailScreen(it, navController) }
        }
    }
}
