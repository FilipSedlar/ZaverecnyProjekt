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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import coil.compose.AsyncImage
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import com.example.zaverecnyprojekt.utils.PreferencesHelper
import androidx.compose.ui.platform.LocalContext

@Composable
fun CountryListScreen(
    viewModel: CountryViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onCountryClick: (Country) -> Unit
) {

    val context = LocalContext.current
    val preferencesHelper = remember { PreferencesHelper(context) }

    val countries by viewModel.countries.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    var selectedRegion by remember { mutableStateOf(preferencesHelper.getSelectedRegion()) } // Předvyplněný region
    var searchQuery by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Nadpis
        Text(
            text = "Explore Countries",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Vyhledávací pole
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text(text = "Search Country") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            singleLine = true
        )

        // Dropdown menu pro výběr regionu
        DropdownMenu(
            regions = listOf("All", "Africa", "Asia", "Europe", "Americas", "Oceania"),
            selectedRegion = selectedRegion,
            onRegionSelected = {
                selectedRegion = it
                preferencesHelper.saveSelectedRegion(it)
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Zobrazení seznamu nebo načítání
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Loading countries...",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        } else {
            // Filtrace podle regionu a vyhledávacího dotazu
            val filteredCountries = countries.filter { country ->
                (selectedRegion == "All" || country.region == selectedRegion) &&
                        country.name.common.contains(searchQuery, ignoreCase = true)
            }

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(filteredCountries) { country ->
                    CountryItem(country = country, onClick = { onCountryClick(country) })
                }
            }
        }
    }
}


@Composable
fun CountryItem(
    country: Country,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 8.dp, vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Vlajka
            AsyncImage(
                model = country.flags.png,
                contentDescription = "${country.name.common} flag",
                modifier = Modifier
                    .size(64.dp)
                    .padding(end = 16.dp)
            )

            // Název země a region
            Column {
                Text(
                    text = country.name.common,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Region: ${country.region}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
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
            .wrapContentSize(Alignment.TopStart)
    ) {
        // Dropdown Trigger Box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(12.dp)
                )
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(12.dp)
                )
                .clickable { expanded = true }
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = selectedRegion,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = null
                )
            }
        }


        androidx.compose.material3.DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            regions.forEach { region ->
                androidx.compose.material3.DropdownMenuItem(
                    text = {
                        Text(
                            text = region,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.fillMaxWidth()
                        )
                    },
                    onClick = {
                        onRegionSelected(region)
                        expanded = false
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            MaterialTheme.colorScheme.surface
                        )
                )
            }
        }
    }
}





