package com.david.gameservice.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.david.gameservice.models.Usuario
import com.david.gameservice.network.RetrofitClientUsuario
import com.david.gameservice.ui.theme.*

@Composable
fun UsersScreen() {
    var usuarios by remember { mutableStateOf<List<Usuario>>(emptyList()) }
    var filtro by remember { mutableStateOf(TextFieldValue("")) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var usuarioSeleccionado by remember { mutableStateOf<Usuario?>(null) }

    LaunchedEffect(Unit) {
        try {
            usuarios = RetrofitClientUsuario.api.obtenerUsuarios()
        } catch (e: Exception) {
            errorMessage = "Error al cargar usuarios: ${e.message}"
        } finally {
            isLoading = false
        }
    }

    val usuariosFiltrados = usuarios.filter {
        it.nickname.contains(filtro.text, ignoreCase = true)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color3)
            .padding(12.dp)
    ) {
        when {
            isLoading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = color5)
            }

            errorMessage != null -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(errorMessage ?: "Error desconocido", color = color2)
            }

            usuarioSeleccionado != null -> {
                PerfilUsuario(usuarioSeleccionado!!) { usuarioSeleccionado = null }
            }

            else -> Column {
                OutlinedTextField(
                    value = filtro,
                    onValueChange = { filtro = it },
                    placeholder = { Text("Buscar usuario...", color = Color.Gray) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = color5,
                        unfocusedBorderColor = Color.Gray,
                        cursorColor = color5
                    )
                )

                LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(usuariosFiltrados) { usuario ->
                        UsuarioCard(usuario) { usuarioSeleccionado = usuario }
                    }
                }
            }
        }
    }
}

@Composable
fun UsuarioCard(usuario: Usuario, onVerPerfil: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E))
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter("https://davidalwaysdata.alwaysdata.net/${usuario.rutaImagen}"),
                contentDescription = usuario.nickname,
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(usuario.nickname, color = color1, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(usuario.firma, color = color4, maxLines = 1, overflow = TextOverflow.Ellipsis)
            }

            Button(
                onClick = onVerPerfil,
                colors = ButtonDefaults.buttonColors(containerColor = color5),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Ver perfil", color = color3)
            }
        }
    }
}

@Composable
fun PerfilUsuario(usuario: Usuario, onCerrar: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color3)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = rememberAsyncImagePainter("https://davidalwaysdata.alwaysdata.net/${usuario.rutaImagen}"),
            contentDescription = usuario.nickname,
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(usuario.nickname, color = color1, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Text(usuario.firma, color = color5, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Correo: ${usuario.correo}", color = color1)
        Text("Registrado: ${usuario.fechaRegistro}", color = Color.Gray, fontSize = 13.sp)
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = onCerrar, colors = ButtonDefaults.buttonColors(containerColor = color2)) {
            Text("Cerrar", color = color1)
        }
    }
}
