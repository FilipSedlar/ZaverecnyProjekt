package com.example.zaverecnyprojekt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.zaverecnyprojekt.ui.theme.ZaverecnyProjektTheme
import com.example.zaverecnyprojekt.view.CountryListScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZaverecnyProjektTheme {
                // Povrch pro vykreslení aplikace
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Hlavní obrazovka aplikace
                    CountryListScreen()
                }
            }
        }
    }
}
