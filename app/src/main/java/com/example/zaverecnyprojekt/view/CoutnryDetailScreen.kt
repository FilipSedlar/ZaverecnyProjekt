package com.example.zaverecnyprojekt.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.zaverecnyprojekt.model.Country

@Composable
fun CountryDetailScreen(country: Country, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Vlajka země
        Image(
            painter = rememberImagePainter(data = country.flags.png),
            contentDescription = "${country.name.common} flag",
            modifier = Modifier.size(150.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Informace o zemi
        Text(text = country.name.official, fontSize = 24.sp)
        Text(text = "Region: ${country.region}", fontSize = 18.sp)
        Text(text = "Main City: ${country.capital?.joinToString() ?: "Neznámé"}", fontSize = 18.sp)
        Text(text = "Population: ${country.population}", fontSize = 18.sp)
        Text(text = "Area: ${country.area} km²", fontSize = 18.sp)
        Text(text = "Languages: ${country.languages?.values?.joinToString() ?: "Neznámé"}", fontSize = 18.sp)

        Spacer(modifier = Modifier.height(32.dp))

        // Tlačítko Zpět
        Button(onClick = { navController.navigateUp() }) {
            Text(text = "Back")
        }
    }
}
