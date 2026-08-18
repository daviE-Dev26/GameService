package com.david.gameservice.utils

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.david.gameservice.dao.DatabaseProvider
import com.david.gameservice.dao.GamesDao
import com.david.gameservice.dao.Videojuego
import com.david.gameservice.ui.theme.color1
import com.david.gameservice.ui.theme.color2
import com.david.gameservice.ui.theme.color3
import com.david.gameservice.ui.theme.color4
import com.david.gameservice.ui.theme.color5

class GamesActivity : ComponentActivity() {
    private lateinit var gamesDao: GamesDao
    class GamesActivity : ComponentActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            // ...
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val database = DatabaseProvider.getDatabase(this)
        gamesDao = database.gamesDao()

        enableEdgeToEdge()
        setContent {
            val gameList = remember { mutableStateOf(listOf<Videojuego>()) }

            LaunchedEffect(Unit) {
                gamesDao.getAllGames().collect { games ->
                    gameList.value = games
                }
            }

            Scaffold(
                containerColor = color3,
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                "Mis Videojuegos",
                                color = color1,
                                fontSize = 20.sp
                            )
                        },
                        colors = TopAppBarDefaults.topAppBarColors(containerColor = color3)
                    )
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = {
                            startActivity(
                                Intent(this@GamesActivity, GamesInsertActivity::class.java)
                            )
                        },
                        containerColor = color5,
                        contentColor = color3
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Agregar")
                    }
                }
            ) { paddingValues ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .background(color3)
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp)
                    ) {
                        items(gameList.value) { game ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 6.dp)
                                    .clickable {
                                        val intent =
                                            Intent(this@GamesActivity, GamesUpdateActivity::class.java)
                                        intent.putExtra("gameId", game.id)
                                        intent.putExtra("gameNombre", game.nombre)
                                        intent.putExtra("gameDesarrollador", game.desarrollador)
                                        intent.putExtra("gamePrecio", game.precio)
                                        intent.putExtra("gameCategoria", game.categoria)
                                        intent.putExtra("gameDescripcion", game.descripcion)
                                        startActivity(intent)
                                    },
                                colors = CardDefaults.cardColors(containerColor = Color(0xFF1C1C1C)),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Text(game.nombre, color = color1, fontSize = 16.sp)
                                    Text("Desarrollador: ${game.desarrollador}", color = color5, fontSize = 14.sp)
                                    Text("Precio: ${game.precio}", color = color4, fontSize = 14.sp)
                                    Text("Categoría: ${game.categoria}", color = color2, fontSize = 14.sp)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(game.descripcion, color = Color.LightGray, fontSize = 12.sp)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
