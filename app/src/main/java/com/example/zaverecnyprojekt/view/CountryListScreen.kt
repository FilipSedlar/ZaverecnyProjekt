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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme

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
        // Nadpis
        Text(
            text = "Explore Countries",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier
                .align(Alignment.CenterHorizontally) // Zarovnání do středu
                .padding(bottom = 16.dp)
        )

        // Vyhledávací pole
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text(text = "Search Country") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            singleLine = true,
            shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
        )


        DropdownMenu(
            regions = listOf("All", "Africa", "Asia", "Europe", "Americas", "Oceania"),
            selectedRegion = selectedRegion,
            onRegionSelected = { selectedRegion = it }
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
                    style = androidx.compose.material3.MaterialTheme.typography.bodyLarge,
                    color = androidx.compose.material3.MaterialTheme.colorScheme.secondary
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
        shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
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
                    style = androidx.compose.material3.MaterialTheme.typography.titleMedium,
                    color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenu(
    regions: List<String>, // Předaný seznam regionů
    selectedRegion: String, // Vybraný region
    onRegionSelected: (String) -> Unit // Callback pro výběr regionu
) {
    var expanded by remember { mutableStateOf(false) } // Stav pro otevření/uzavření dropdownu

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopStart) // Zarovnání dropdownu
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
                .clickable { expanded = true } // Kliknutím se otevře menu
                .padding(12.dp) // Vnitřní okraje pro lepší vzhled
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = selectedRegion, // Zobrazení vybraného regionu
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.weight(1f) // Vyplní šířku, aby byla šipka zarovnaná vpravo
                )
                Icon(
                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = null
                )
            }
        }

        // Dropdown Menu Items
        androidx.compose.material3.DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }, // Zavření menu při kliknutí mimo něj
            modifier = Modifier
                .fillMaxWidth() // Zajištění, že menu má stejnou šířku jako trigger
        ) {
            regions.forEach { region ->
                androidx.compose.material3.DropdownMenuItem(
                    text = {
                        Text(
                            text = region,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.fillMaxWidth() // Položka se rozprostře přes celou šířku
                        )
                    },
                    onClick = {
                        onRegionSelected(region) // Callback při výběru
                        expanded = false // Zavření menu
                    },
                    modifier = Modifier
                        .fillMaxWidth() // Zajištění, že kliknutelný prostor je roztažený
                        .background(
                            MaterialTheme.colorScheme.surface
                        )
                )
            }
        }
    }
}





