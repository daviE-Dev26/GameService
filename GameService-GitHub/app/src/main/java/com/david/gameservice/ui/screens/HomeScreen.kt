package com.david.gameservice.ui.screens

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.david.gameservice.data.DataStoreManager
import com.david.gameservice.models.Videojuego
import com.david.gameservice.network.RetrofitClientVideojuego
import com.david.gameservice.ui.theme.color1
import com.david.gameservice.ui.theme.color2
import com.david.gameservice.ui.theme.color3
import com.david.gameservice.ui.theme.color4
import com.david.gameservice.ui.theme.color5
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    username: String,
    dataStoreManager: DataStoreManager,
    onLogout: () -> Unit
) {
    var currentTab by remember { mutableStateOf("Home") }
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val context = LocalContext.current

    // Drawer lateral parcial
    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = true,
        scrimColor = Color.Black.copy(alpha = 0.5f),
        drawerContent = {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(260.dp)
                    .background(color3)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Menú",
                        color = color1,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )

                    Text(
                        text = "Ubicaciones",
                        color = color5,
                        fontSize = 18.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                scope.launch { drawerState.close() }
                                context.startActivity(Intent(context, LocationsListActivity::class.java))
                            }
                            .padding(vertical = 12.dp)
                    )
                }
            }
        }
    ) {
        Scaffold(
            containerColor = color3,
            topBar = {
                TopAppBar(
                    title = {
                        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                            Text(
                                text = if (username.isBlank() || username == "Invitado")
                                    "Bienvenido a GameService"
                                else
                                    "Bienvenido, $username 👋",
                                color = color1,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch { drawerState.open() }
                        }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menú", tint = color1)
                        }
                    },
                    actions = {
                        IconButton(onClick = { /* carrito */ }) {
                            Icon(Icons.Default.ShoppingCart, contentDescription = "Carrito", tint = color1)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = color3)
                )
            },
            bottomBar = {
                NavigationBar(containerColor = color3) {
                    val items = listOf(
                        "Home" to Icons.Default.Home,
                        "Explorar" to Icons.Default.Search,
                        "Usuarios" to Icons.Default.Group,
                        "Perfil" to Icons.Default.Person
                    )

                    items.forEach { (tab, icon) ->
                        val selected = currentTab == tab
                        NavigationBarItem(
                            selected = selected,
                            onClick = { currentTab = tab },
                            icon = {
                                Icon(
                                    imageVector = icon,
                                    contentDescription = tab,
                                    tint = if (selected) color5 else color1
                                )
                            },
                            label = { Text(tab, color = if (selected) color5 else color1) },
                            alwaysShowLabel = true,
                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor = Color.Transparent
                            )
                        )
                    }
                }
            }
        ) { padding ->
            Box(
                modifier = Modifier
                    .padding(padding)
                    .background(color3)
                    .fillMaxSize()
            ) {
                when (currentTab) {
                    "Home" -> HomeContent()
                    "Explorar" -> ExploreScreen()
                    "Usuarios" -> UsersScreen()
                    "Perfil" -> ProfileScreen(
                        username = username,
                        dataStoreManager = dataStoreManager,
                        onLogout = {
                            scope.launch {
                                dataStoreManager.setUserLogged(false)
                                dataStoreManager.setUsername("")
                            }
                            onLogout()
                        },
                        onLoginClick = { onLogout() }
                    )
                }
            }
        }
    }
}



@Composable
fun HomeContent() {
    var juegos by remember { mutableStateOf<List<Videojuego>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var searchQuery by remember { mutableStateOf("") }

    // Cargar desde API
    LaunchedEffect(Unit) {
        try {
            juegos = RetrofitClientVideojuego.api.obtenerVideojuegos()
        } catch (e: Exception) {
            errorMessage = "Error al cargar videojuegos: ${e.message}"
        } finally {
            isLoading = false
        }
    }

    // Filtrar juegos según la búsqueda
    val juegosFiltrados = juegos.filter {
        it.nombre.contains(searchQuery, ignoreCase = true) ||
                it.nombreCategoria.contains(searchQuery, ignoreCase = true)
    }

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
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color3)
                    .padding(horizontal = 12.dp)
            ) {
                // 🔍 Barra de búsqueda funcional
                item {
                    SearchBar(
                        query = searchQuery,
                        onQueryChange = { searchQuery = it }
                    )
                }

                if (juegosFiltrados.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier.fillMaxWidth().padding(20.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("No se encontraron resultados 😢", color = color2)
                        }
                    }
                } else {
                    // 🔥 Novedades
                    item {
                        SectionTitle("Novedades 🎯")
                        LazyRow {
                            items(juegosFiltrados.take(6)) { juego ->
                                GameCardHorizontal(juego)
                            }
                        }
                    }

                    // ⭐ Recomendados
                    item {
                        Spacer(modifier = Modifier.height(12.dp))
                        SectionTitle("Recomendados ⭐")
                    }

                    items(juegosFiltrados.takeLast(6)) { juego ->
                        GameCardVertical(juego)
                    }
                }
            }
        }
    }
}

@Composable
fun SearchBar(query: String, onQueryChange: (String) -> Unit) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        placeholder = { Text("Buscar por nombre o categoría...", color = Color.Gray) },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Buscar", tint = color5) },
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = color1,
            unfocusedTextColor = color1,
            cursorColor = color5,
            focusedBorderColor = color5,
            unfocusedBorderColor = Color.Gray
        ),
        shape = RoundedCornerShape(50),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
    )
}

@Composable
fun SectionTitle(text: String) {
    Text(
        text = text,
        color = color4,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

@Composable
fun GameCardHorizontal(juego: Videojuego) {
    Card(
        modifier = Modifier
            .width(160.dp)
            .padding(6.dp)
            .clickable { /* futuro detalle */ },
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1C1C1C))
    ) {
        Column {
            Image(
                painter = rememberAsyncImagePainter("https://davidalwaysdata.alwaysdata.net/${juego.rutaImagen}"),
                contentDescription = juego.nombre,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
            )
            Text(
                juego.nombre,
                color = color1,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
            )
            Text(
                "S/ ${juego.precio}",
                color = color5,
                fontSize = 13.sp,
                modifier = Modifier.padding(start = 6.dp, bottom = 4.dp)
            )
        }
    }
}

@Composable
fun GameCardVertical(juego: Videojuego) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { /* futuro detalle */ },
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1C1C1C))
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = rememberAsyncImagePainter("https://davidalwaysdata.alwaysdata.net/${juego.rutaImagen}"),
                contentDescription = juego.nombre,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .padding(6.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Column(modifier = Modifier.padding(6.dp)) {
                Text(juego.nombre, color = color1, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text("S/ ${juego.precio}", color = color5, fontSize = 15.sp)
                Text(
                    juego.descripcion,
                    color = Color.LightGray,
                    fontSize = 12.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun ExploreContent() {
    Box(modifier = Modifier.fillMaxSize().background(color3), contentAlignment = Alignment.Center) {
        Text("Explorar videojuegos 🔍", color = color1)
    }
}

@Composable
fun UsersContent() {
    Box(modifier = Modifier.fillMaxSize().background(color3), contentAlignment = Alignment.Center) {
        Text("Usuarios conectados 👥", color = color1)
    }
}

@Composable
fun ProfileContent(username: String, onLogout: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color3)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(Icons.Default.Person, contentDescription = null, tint = color1, modifier = Modifier.size(80.dp))
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            if (username.isBlank() || username == "Invitado") "Invitado" else username,
            color = color1,
            fontSize = 22.sp
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = { onLogout() },
            colors = ButtonDefaults.buttonColors(containerColor = color5)
        ) {
            Text("Cerrar sesión", color = color3)
        }
    }
}
