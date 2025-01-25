package com.example.zaverecnyprojekt.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.zaverecnyprojekt.model.Country

@Composable
fun CountryListScreen(countries: List<Country>, onCountryClick: (Country) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(countries) { country ->
            CountryItem(country, onClick = { onCountryClick(country) })
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
        // Vlajka
        androidx.compose.foundation.Image(
            painter = coil.compose.rememberImagePainter(data = country.flags.png),
            contentDescription = "${country.name.common} flag",
            modifier = Modifier.size(64.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Název země
        Text(text = country.name.common, fontSize = 20.sp)
    }
}
