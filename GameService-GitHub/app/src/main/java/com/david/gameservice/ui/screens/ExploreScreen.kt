package com.david.gameservice.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.david.gameservice.models.Videojuego
import com.david.gameservice.network.RetrofitClientVideojuego
import com.david.gameservice.ui.theme.*

@Composable
fun ExploreScreen() {
    var juegos by remember { mutableStateOf<List<Videojuego>>(emptyList()) }
    var filtroSeleccionado by remember { mutableStateOf("Todos") }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val filtros = listOf("Todos", "Acción", "Aventura", "Deportes", "Estrategia", "Terror", "Tendencias")

    // Carga desde API
    LaunchedEffect(Unit) {
        try {
            juegos = RetrofitClientVideojuego.api.obtenerVideojuegos()
        } catch (e: Exception) {
            errorMessage = "Error al cargar: ${e.message}"
        } finally {
            isLoading = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color3)
            .padding(horizontal = 12.dp)
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        // 🔘 Lista horizontal de filtros
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(filtros) { filtro ->
                val seleccionado = filtro == filtroSeleccionado
                FilterChip(
                    selected = seleccionado,
                    onClick = { filtroSeleccionado = filtro },
                    label = {
                        Text(
                            filtro,
                            color = if (seleccionado) color3 else color1,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = color5,
                        containerColor = Color.DarkGray
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // 📦 Lista de videojuegos según filtro
        when {
            isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = color5)
                }
            }

            errorMessage != null -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(errorMessage ?: "Error desconocido", color = color2)
                }
            }

            else -> {
                val juegosFiltrados = when (filtroSeleccionado) {
                    "Todos" -> juegos
                    "Tendencias" -> juegos.shuffled().take(6) // simula tendencias
                    else -> juegos.filter { it.nombreCategoria.contains(filtroSeleccionado, ignoreCase = true) }
                }

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(juegosFiltrados) { juego ->
                        ExploreGameCard(juego)
                    }
                }
            }
        }
    }
}

@Composable
fun ExploreGameCard(juego: Videojuego) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* futuro detalle */ },
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1C1C1C))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter("https://davidalwaysdata.alwaysdata.net/${juego.rutaImagen}"),
                contentDescription = juego.nombre,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(90.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.width(10.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = juego.nombre,
                    color = color1,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = juego.nombreCategoria,
                    color = color5,
                    fontSize = 13.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "S/ ${juego.precio}",
                    color = color4,
                    fontSize = 14.sp
                )
                Text(
                    text = juego.descripcion,
                    color = Color.Gray,
                    fontSize = 12.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
