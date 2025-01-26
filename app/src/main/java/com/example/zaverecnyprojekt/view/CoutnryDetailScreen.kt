package com.example.zaverecnyprojekt.view

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.zaverecnyprojekt.model.Country
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController

@Composable
fun CountryDetailScreen(country: Country, navController: NavHostController) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Vlajka
        AsyncImage(
            model = country.flags.png,
            contentDescription = "Flag of ${country.name.common}",
            modifier = Modifier
                .size(150.dp)
                .padding(bottom = 16.dp)
        )

        // Název země
        Text(
            text = country.name.common,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Tabulka detailů
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            DetailRow(label = "Region:", value = country.region)
            DetailRow(label = "Capital City:", value = country.capital?.firstOrNull() ?: "Unknown")
            DetailRow(label = "Population:", value = country.population.toString())
            DetailRow(label = "Area:", value = "${country.area} km²")
            DetailRow(
                label = "Languages:",
                value = country.languages?.values?.joinToString(", ") ?: "Unknown"
            )
            DetailRow(
                label = "Time Zones:",
                value = country.timezones?.joinToString(", ") ?: "Unknown"
            )
            DetailRow(
                label = "Currency:",
                value = country.currencies?.values?.joinToString(", ") { "${it.name} (${it.symbol})" } ?: "Unknown"
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        country.maps?.get("googleMaps")?.let { mapUrl ->
            val context = LocalContext.current
            Button(
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(mapUrl))
                    context.startActivity(intent)
                },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(text = "View on Google Maps")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Tlačítko zpět
        Button(onClick = { navController.popBackStack() }) {
            Text(text = "Back")
        }
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(2f),
            textAlign = TextAlign.End,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
}
