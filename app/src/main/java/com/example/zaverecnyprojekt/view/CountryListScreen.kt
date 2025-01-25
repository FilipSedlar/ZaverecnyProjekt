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
import androidx.compose.ui.Alignment
import com.example.zaverecnyprojekt.viewmodel.CountryViewModel

@Composable
fun CountryListScreen(
    viewModel: CountryViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onCountryClick: (Country) -> Unit
) {
    val countries by viewModel.countries.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    var selectedRegion by remember { mutableStateOf("All") } // Výchozí region
    var searchQuery by remember { mutableStateOf("") } // Vyhledávací dotaz

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Vyhledávací pole
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text(text = "Find Country") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Dropdown menu pro filtrování podle regionu
        DropdownMenu(
            regions = listOf("All", "Africa", "Asia", "Europe", "Americas", "Oceania"),
            selectedRegion = selectedRegion,
            onRegionSelected = { selectedRegion = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Zobrazování seznamu nebo stav načítání
        if (isLoading) {
            Text(text = "Loading countries...", modifier = Modifier.padding(16.dp))
        } else {
            // Filtrování podle regionu a vyhledávacího dotazu
            val filteredCountries = countries.filter { country ->
                (selectedRegion == "All" || country.region == selectedRegion) &&
                        country.name.common.contains(searchQuery, ignoreCase = true)
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filteredCountries) { country ->
                    CountryItem(
                        country = country,
                        onClick = { onCountryClick(country) }
                    )
                }
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
            .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
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

@Composable
fun DropdownMenu(
    regions: List<String>,
    selectedRegion: String,
    onRegionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { expanded = !expanded }
    ) {
        Text(
            text = "Region: $selectedRegion",
            modifier = Modifier.padding(8.dp)
        )
        androidx.compose.material.DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            regions.forEach { region ->
                androidx.compose.material.DropdownMenuItem(
                    onClick = {
                        onRegionSelected(region)
                        expanded = false
                    }
                ) {
                    Text(text = region)
                }
            }
        }
    }
}


