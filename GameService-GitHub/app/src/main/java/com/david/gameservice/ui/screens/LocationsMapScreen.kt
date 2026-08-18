package com.david.gameservice.ui.screens

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import com.david.gameservice.models.GameLocation
import com.david.gameservice.network.RetrofitClientLocations
import com.david.gameservice.ui.theme.color1
import com.david.gameservice.ui.theme.color3
import com.david.gameservice.ui.theme.color5
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState


class LocationsMapActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LocationsMapScreen(
                onBack = { finish() },
                onCircleClick = { place ->
                    val intent = Intent(this, LocationDetailActivity::class.java)
                    intent.putExtra("id", place.id)
                    intent.putExtra("nombre", place.nombre)
                    intent.putExtra("latitud", place.latitud)
                    intent.putExtra("longitud", place.longitud)
                    intent.putExtra("imagen", place.imagen)
                    intent.putExtra("descripcion", place.descripcion)
                    startActivity(intent)
                }

            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationsMapScreen(
    onBack: () -> Unit,
    onCircleClick: (GameLocation) -> Unit
) {

    var locations by remember { mutableStateOf<List<GameLocation>>(emptyList()) }

    LaunchedEffect(Unit) {
        locations = RetrofitClientLocations.api.obtenerUbicaciones()
        locations.forEach {
            Log.d("API_DEBUG", "id=${it.id}, nombre=${it.nombre}, imagen=${it.imagen}, descripcion=${it.descripcion}")
        }
    }

    Scaffold(
        containerColor = color3,
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "volver", tint = color1)
                    }
                },
                title = { Text("Mapa de tiendas", color = color1) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = color3)
            )
        }
    ) { padding ->

        if (locations.isEmpty()) return@Scaffold

        val cameraPosition = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(
                LatLng(locations[0].latitud, locations[0].longitud), 14f
            )
        }

        GoogleMap(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            cameraPositionState = cameraPosition
        ) {

            locations.forEach { place ->

                // marcador
                Marker(
                    state = MarkerState(
                        position = LatLng(place.latitud, place.longitud)
                    ),
                    title = place.nombre
                )

                // círculo clickeable
                Circle(
                    center = LatLng(place.latitud, place.longitud),
                    radius = 70.0,
                    fillColor = Color(0x559AEF5E),
                    strokeColor = color5,
                    strokeWidth = 3f,
                    clickable = true,
                    onClick = {
                        onCircleClick(place)
                    }
                )
            }
        }
    }
}
