package com.example.zaverecnyprojekt.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.zaverecnyprojekt.model.Country
import androidx.compose.foundation.clickable

@Composable
fun CountryListScreen(
    countries: List<Country>,
    onCountryClick: (Country) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }

    // Filtrování zemí podle vyhledávacího dotazu
    val filteredCountries = countries.filter { country ->
        country.name.common.contains(searchQuery, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text(text = "Find Country") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))


        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(filteredCountries) { country ->
                CountryItem(country, onClick = { onCountryClick(country) })
            }
        }
    }
}

@Composable
fun CountryItem(country: Country, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(8.dp)
    ) {

        androidx.compose.foundation.Image(
            painter = coil.compose.rememberImagePainter(data = country.flags.png),
            contentDescription = "${country.name.common} flag",
            modifier = Modifier.size(64.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))


        Text(text = country.name.common)
    }
}
