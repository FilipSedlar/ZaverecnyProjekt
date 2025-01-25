package com.example.zaverecnyprojekt.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.example.zaverecnyprojekt.model.Country
import com.example.zaverecnyprojekt.viewmodel.CountryViewModel

@Composable
fun CountryListScreen(
    viewModel: CountryViewModel = viewModel()
) {
    val countries = viewModel.countries.collectAsState()
    val isLoading = viewModel.isLoading.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (isLoading.value) {
            CircularProgressIndicator() // Indikátor načítání
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(countries.value) { country ->
                    CountryItem(country)
                }
            }
        }
    }
}

@Composable
fun CountryItem(country: Country) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Vlajka
        Image(
            painter = rememberImagePainter(data = country.flags.png),
            contentDescription = "${country.name.common} flag",
            modifier = Modifier.size(64.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Informace o zemi
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(text = country.name.common, fontSize = 20.sp)
            Text(text = "Region: ${country.region}", fontSize = 14.sp)
            Text(text = "Hlavní město: ${country.capital?.joinToString() ?: "Neznámé"}", fontSize = 14.sp)
            Text(text = "Populace: ${country.population}", fontSize = 14.sp)
        }
    }
}
