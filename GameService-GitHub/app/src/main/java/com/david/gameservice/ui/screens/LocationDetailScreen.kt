package com.david.gameservice.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.david.gameservice.ui.theme.color1
import com.david.gameservice.ui.theme.color3
import com.david.gameservice.ui.theme.color4


class LocationDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val nombre = intent.getStringExtra("nombre") ?: ""
        val imagen = intent.getStringExtra("imagen") ?: ""
        val descripcion = intent.getStringExtra("descripcion") ?: ""
        val latitud = intent.getDoubleExtra("latitud", 0.0)
        val longitud = intent.getDoubleExtra("longitud", 0.0)


        setContent {
            LocationDetailScreen(
                nombre = nombre,
                imagen = imagen,
                descripcion = descripcion,
                latitud = latitud,
                longitud = longitud,
                onBack = { finish() }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationDetailScreen(
    nombre: String,
    imagen: String,
    descripcion: String,
    latitud: Double? = null,
    longitud: Double? = null,
    onBack: () -> Unit
) {
    Scaffold(
        containerColor = color3,
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "volver", tint = color1)
                    }
                },
                title = {
                    Text(
                        text = nombre,
                        color = color1,
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = color3)
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(20.dp)
        ) {

            // ---------- IMAGEN ----------
            val baseUrl = "https://davidalwaysdata.alwaysdata.net/"
            val fullImageUrl = imagen.takeIf { it.isNotBlank() }?.let { baseUrl + it }

            AsyncImage(
                model = fullImageUrl,
                contentDescription = nombre,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .padding(bottom = 16.dp)
            )

            // ---------- DESCRIPCIÓN ----------
            Text(
                text = descripcion,
                color = color1,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // ---------- COORDENADAS ----------
            if (latitud != null && longitud != null) {
                Text(
                    text = "Ubicación:",
                    color = color4,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = "Latitud: $latitud",
                    color = color1,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Longitud: $longitud",
                    color = color1,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
