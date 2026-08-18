package com.david.gameservice.ui.screens

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.david.gameservice.models.GameLocation
import com.david.gameservice.network.RetrofitClientLocations
import com.david.gameservice.ui.theme.color1
import com.david.gameservice.ui.theme.color2
import com.david.gameservice.ui.theme.color3
import com.david.gameservice.ui.theme.color5


class LocationsListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LocationsListScreen(
                onGoToMap = {
                    startActivity(Intent(this, LocationsMapActivity::class.java))
                },
                onBack = { finish() }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationsListScreen(
    onGoToMap: () -> Unit,
    onBack: () -> Unit // ✅ Agregamos la función para volver
) {

    var locations by remember { mutableStateOf<List<GameLocation>>(emptyList()) }
    var loading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        try {
            locations = RetrofitClientLocations.api.obtenerUbicaciones()
        } catch (e: Exception) {
            error = "Error cargando ubicaciones: ${e.message}"
        } finally {
            loading = false
        }
    }

    Scaffold(
        containerColor = color3,
        topBar = {
            TopAppBar(
                title = { Text("Tiendas de videojuegos", color = color1) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "volver", tint = color1)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = color3)
            )
        }
    ) { padding ->

        Column(
            Modifier
                .padding(padding)
                .fillMaxSize()
                .background(color3)
                .padding(12.dp)
        ) {

            when {
                loading -> {
                    CircularProgressIndicator(color = color5)
                }

                error != null -> {
                    Text(error!!, color = color2)
                }

                else -> {
                    LazyColumn {
                        items(locations) { item ->

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 6.dp)
                                    .clickable {
                                        // Aquí podrías abrir detalles si quieres
                                    },
                                colors = CardDefaults.cardColors(containerColor = Color.DarkGray)
                            ) {
                                Column(Modifier.padding(12.dp)) {
                                    Text(item.nombre, color = color5, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                                    Text("Lat: ${item.latitud}", color = color1)
                                    Text("Lon: ${item.longitud}", color = color1)
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = onGoToMap,
                        colors = ButtonDefaults.buttonColors(containerColor = color5),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Ver mapa", color = color3, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
