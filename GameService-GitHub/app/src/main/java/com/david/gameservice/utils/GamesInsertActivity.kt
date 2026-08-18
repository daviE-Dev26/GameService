package com.david.gameservice.utils

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.david.gameservice.dao.DatabaseProvider
import com.david.gameservice.dao.GamesDao
import com.david.gameservice.dao.Videojuego
import com.david.gameservice.ui.theme.color1
import com.david.gameservice.ui.theme.color2
import com.david.gameservice.ui.theme.color3
import com.david.gameservice.ui.theme.color4
import com.david.gameservice.ui.theme.color5
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
class GamesInsertActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            var nombre by remember { mutableStateOf("") }
            var desarrollador by remember { mutableStateOf("") }
            var precio by remember { mutableStateOf("") }
            var categoria by remember { mutableStateOf("") }
            var descripcion by remember { mutableStateOf("") }

            Scaffold(
                containerColor = color3,
                topBar = {
                    TopAppBar(
                        title = { Text("Agregar Videojuego", color = color1, fontSize = 20.sp) },
                        colors = TopAppBarDefaults.topAppBarColors(containerColor = color3)
                    )
                }
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(24.dp)
                        .background(color3)
                ) {
                    val textFieldModifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)

                    OutlinedTextField(
                        value = nombre,
                        onValueChange = { nombre = it },
                        label = { Text("Nombre", color = color4) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = color1,
                            unfocusedTextColor = color1,
                            cursorColor = color5,
                            focusedBorderColor = color5,
                            unfocusedBorderColor = Color.Gray
                        ),
                        modifier = textFieldModifier
                    )

                    OutlinedTextField(
                        value = desarrollador,
                        onValueChange = { desarrollador = it },
                        label = { Text("Desarrollador", color = color4) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = color1,
                            unfocusedTextColor = color1,
                            cursorColor = color5,
                            focusedBorderColor = color5,
                            unfocusedBorderColor = Color.Gray
                        ),
                        modifier = textFieldModifier
                    )

                    OutlinedTextField(
                        value = precio,
                        onValueChange = { precio = it },
                        label = { Text("Precio", color = color4) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = color1,
                            unfocusedTextColor = color1,
                            cursorColor = color5,
                            focusedBorderColor = color5,
                            unfocusedBorderColor = Color.Gray
                        ),
                        modifier = textFieldModifier
                    )

                    OutlinedTextField(
                        value = categoria,
                        onValueChange = { categoria = it },
                        label = { Text("Categoría", color = color4) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = color1,
                            unfocusedTextColor = color1,
                            cursorColor = color5,
                            focusedBorderColor = color5,
                            unfocusedBorderColor = Color.Gray
                        ),
                        modifier = textFieldModifier
                    )

                    OutlinedTextField(
                        value = descripcion,
                        onValueChange = { descripcion = it },
                        label = { Text("Descripción", color = color4) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = color1,
                            unfocusedTextColor = color1,
                            cursorColor = color5,
                            focusedBorderColor = color5,
                            unfocusedBorderColor = Color.Gray
                        ),
                        modifier = textFieldModifier
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            val database = DatabaseProvider.getDatabase(this@GamesInsertActivity)
                            val gamesDao: GamesDao = database.gamesDao()
                            lifecycleScope.launch {
                                val juego = Videojuego(
                                    nombre = nombre,
                                    desarrollador = desarrollador,
                                    precio = precio,
                                    categoria = categoria,
                                    descripcion = descripcion
                                )
                                gamesDao.insertGame(juego)
                                finish()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = color5, contentColor = color3),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Guardar", fontSize = 16.sp)
                    }
                }
            }
        }
    }
}
