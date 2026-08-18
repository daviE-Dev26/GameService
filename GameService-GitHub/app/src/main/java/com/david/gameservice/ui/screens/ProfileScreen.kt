package com.david.gameservice.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Login
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.david.gameservice.data.DataStoreManager
import com.david.gameservice.models.Usuario
import com.david.gameservice.network.RetrofitClientUsuario
import com.david.gameservice.ui.theme.color1
import com.david.gameservice.ui.theme.color3
import com.david.gameservice.ui.theme.color4
import com.david.gameservice.ui.theme.color5
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
    username: String,
    dataStoreManager: DataStoreManager,
    onLogout: () -> Unit,
    onLoginClick: () -> Unit
) {
    var usuario by remember { mutableStateOf<Usuario?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    // Detectar si es invitado
    val isGuest = username.isBlank() || username.equals("invitado", ignoreCase = true)

    // Cargar datos solo si NO es invitado
    LaunchedEffect(username) {
        if (!isGuest) {
            try {
                val listaUsuarios = RetrofitClientUsuario.api.obtenerUsuarios()
                usuario = listaUsuarios.find { it.nickname == username || it.correo == username }
            } catch (e: Exception) {
                errorMessage = "Error al cargar el perfil: ${e.message}"
            } finally {
                isLoading = false
            }
        } else {
            isLoading = false
        }
    }

    when {
        isLoading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = color5)
            }
        }

        errorMessage != null -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(errorMessage ?: "Error desconocido", color = Color.Red)
            }
        }

        isGuest -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color3),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "No has iniciado sesión",
                        color = color1,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Spacer(Modifier.height(16.dp))
                    Button(
                        onClick = { onLoginClick() },
                        colors = ButtonDefaults.buttonColors(containerColor = color5)
                    ) {
                        Icon(Icons.Default.Login, contentDescription = "Iniciar sesión", tint = color3)
                        Spacer(Modifier.width(8.dp))
                        Text("Iniciar sesión", color = color3)
                    }
                }
            }
        }

        usuario != null -> {
            val u = usuario!!
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color3)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Spacer(modifier = Modifier.height(20.dp))

                Image(
                    painter = rememberAsyncImagePainter("https://davidalwaysdata.alwaysdata.net/${u.rutaImagen}"),
                    contentDescription = u.nickname,
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(u.nickname, color = color1, fontWeight = FontWeight.Bold, fontSize = MaterialTheme.typography.headlineSmall.fontSize)
                Text(u.firma, color = color4)
                Text(u.correo, color = Color.LightGray)
                Text("Miembro desde ${u.fechaRegistro.take(10)}", color = color5)

                Spacer(modifier = Modifier.height(40.dp))

                Button(
                    onClick = {
                        scope.launch {
                            dataStoreManager.setUserLogged(false)
                            dataStoreManager.setUsername("")
                        }
                        onLogout()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = color5)
                ) {
                    Icon(Icons.Default.Logout, contentDescription = "Cerrar sesión", tint = color3)
                    Spacer(Modifier.width(8.dp))
                    Text("Cerrar sesión", color = color3)
                }
            }
        }

        else -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Usuario no encontrado", color = color4)
                    Spacer(Modifier.height(16.dp))
                    Button(
                        onClick = { onLoginClick() },
                        colors = ButtonDefaults.buttonColors(containerColor = color5)
                    ) {
                        Icon(Icons.Default.Login, contentDescription = "Iniciar sesión", tint = color3)
                        Spacer(Modifier.width(8.dp))
                        Text("Iniciar sesión", color = color3)
                    }
                }
            }
        }
    }
}
