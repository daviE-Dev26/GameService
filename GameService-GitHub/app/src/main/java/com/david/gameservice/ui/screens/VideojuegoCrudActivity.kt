package com.david.gameservice.ui.screens

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.david.gameservice.models.Videojuego
import com.david.gameservice.ui.theme.GameServiceTheme
import com.david.gameservice.utils.API_URL
import com.david.gameservice.utils.VideojuegoService
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/** 🔹 Cliente Retrofit usando VideojuegoService **/
object RetrofitVideojuegoClient {
    val api: VideojuegoService by lazy {
        Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(VideojuegoService::class.java)
    }
}

/** 🔹 Activity principal **/
class VideojuegoCrudActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GameServiceTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    VideojuegoCrudScreen()
                }
            }
        }
    }
}

/** 🔹 Pantalla principal **/
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideojuegoCrudScreen() {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var videojuegos by remember { mutableStateOf<List<Videojuego>>(emptyList()) }
    var isDialogOpen by remember { mutableStateOf(false) }
    var selectedVideojuego by remember { mutableStateOf<Videojuego?>(null) }


    val verde = Color(0xFF9AEF5E)
    val blanco = Color.White
    val fondo = Color.Black

    /** Cargar lista **/
    fun cargarLista() {
        scope.launch {
            try {
                videojuegos = RetrofitVideojuegoClient.api.obtenerVideojuegos()
            } catch (e: Exception) {
                Toast.makeText(context, "Error al cargar lista: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    LaunchedEffect(Unit) { cargarLista() }

    /** Editar **/
    fun onEdit(videojuego: Videojuego) {
        selectedVideojuego = videojuego
        isDialogOpen = true
    }

    /** Cambiar estado (activar/desactivar) **/
    fun onToggleActivo(id: Int) {
        scope.launch {
            try {
                val response: Response<ResponseBody> = RetrofitVideojuegoClient.api.eliminarVideojuego(id)
                if (response.isSuccessful) {
                    Toast.makeText(context,"Cambio de estado realizado", Toast.LENGTH_SHORT).show()
                    cargarLista()
                } else {
                    Toast.makeText(context,"Error al cambiar estado: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context,"Error al cambiar estado: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    Scaffold(
        containerColor = fondo,
        topBar = {
            TopAppBar(
                title = { Text("Gestión de Videojuegos", color = blanco) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = verde)
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                selectedVideojuego = null
                isDialogOpen = true
            }, containerColor = verde) {
                Icon(Icons.Default.Add, contentDescription = "Agregar")
            }
        }
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            if (videojuegos.isEmpty()) {
                Text("No hay videojuegos registrados", color = blanco)
            } else {
                LazyColumn {
                    items(videojuegos) { videojuego ->
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                Modifier
                                    .size(12.dp)
                                    .background(
                                        if (videojuego.activo == 1) verde else Color.Red,
                                        shape = CircleShape
                                    )
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(videojuego.nombre, color = blanco, modifier = Modifier.weight(1f))
                            IconButton(onClick = { onEdit(videojuego) }) {
                                Icon(Icons.Default.Edit, contentDescription = "Editar", tint = verde)
                            }
                            IconButton(onClick = { onToggleActivo(videojuego.id) }) {
                                Icon(
                                    Icons.Default.Delete,
                                    contentDescription = "Cambiar estado",
                                    tint = if (videojuego.activo == 1) Color.Red else verde
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    /** Diálogo **/
    if (isDialogOpen) {
        VideojuegoDialog(
            videojuego = selectedVideojuego,
            onDismiss = { isDialogOpen = false },
            onSave = { nuevo ->
                scope.launch {
                    try {
                        val response: Response<ResponseBody> = if (nuevo.id == 0) {
                            RetrofitVideojuegoClient.api.agregarVideojuego(
                                nombre = nuevo.nombre,
                                descripcion = nuevo.descripcion,
                                fechaLanzamiento = nuevo.fechaLanzamiento,
                                desarrollador = nuevo.desarrollador,
                                logrosTotales = nuevo.logrosTotales,
                                precio = nuevo.precio,           // primero precio
                                rutaImagen = nuevo.rutaImagen,
                                rutaImagenGrande = nuevo.rutaImagenGrande,
                                idCategoria = nuevo.idCategoria
                            )
                        } else {
                            RetrofitVideojuegoClient.api.actualizarVideojuego(
                                id = nuevo.id,
                                nombre = nuevo.nombre,
                                descripcion = nuevo.descripcion,
                                fechaLanzamiento = nuevo.fechaLanzamiento,
                                desarrollador = nuevo.desarrollador,
                                logrosTotales = nuevo.logrosTotales,
                                precio = nuevo.precio,           // primero precio
                                rutaImagen = nuevo.rutaImagen,
                                rutaImagenGrande = nuevo.rutaImagenGrande,
                                idCategoria = nuevo.idCategoria
                            )
                        }



                        if(response.isSuccessful){
                            Toast.makeText(context, if(nuevo.id==0) "Agregado correctamente" else "Actualizado correctamente", Toast.LENGTH_SHORT).show()
                            cargarLista()
                            isDialogOpen = false
                        } else {
                            Toast.makeText(context, "Error al guardar: ${response.code()}", Toast.LENGTH_SHORT).show()
                        }

                    } catch (e: Exception) {
                        Toast.makeText(context, "Error al guardar: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        )
    }

}

/** 🔹 Diálogo agregar / editar **/
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideojuegoDialog(
    videojuego: Videojuego?,
    onDismiss: () -> Unit,
    onSave: (Videojuego) -> Unit
) {
    val verde = Color(0xFF9AEF5E)
    val blanco = Color.White
    val fondo = Color(0xFF2C2C2C)


    var id by remember { mutableStateOf(TextFieldValue(videojuego?.id?.toString() ?: "0")) }
    var nombre by remember { mutableStateOf(TextFieldValue(videojuego?.nombre ?: "")) }
    var descripcion by remember { mutableStateOf(TextFieldValue(videojuego?.descripcion ?: "")) }
    var fecha by remember { mutableStateOf(TextFieldValue(videojuego?.fechaLanzamiento ?: "")) }
    var desarrollador by remember { mutableStateOf(TextFieldValue(videojuego?.desarrollador ?: "")) }
    var logros by remember { mutableStateOf(TextFieldValue(videojuego?.logrosTotales?.toString() ?: "")) }
    var rutaImg by remember { mutableStateOf(TextFieldValue(videojuego?.rutaImagen ?: "")) }
    var rutaImgGrande by remember { mutableStateOf(TextFieldValue(videojuego?.rutaImagenGrande ?: "")) }
    var idCategoria by remember { mutableStateOf(TextFieldValue(videojuego?.idCategoria?.toString() ?: "1")) }
    var precio by remember { mutableStateOf(TextFieldValue(videojuego?.precio ?: "")) }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = fondo,
        title = { Text(if (videojuego == null) "Agregar videojuego" else "Editar videojuego", color = verde) },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                val colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = verde,
                    unfocusedBorderColor = blanco,
                    cursorColor = verde,
                    focusedTextColor = blanco,
                    unfocusedTextColor = blanco,
                    focusedLabelColor = verde,
                    unfocusedLabelColor = blanco,
                    disabledTextColor = blanco,
                    disabledBorderColor = verde,
                    disabledLabelColor = verde
                )

                OutlinedTextField(
                    value = id,
                    onValueChange = {},
                    label = { Text("ID (solo lectura)") },
                    enabled = false,
                    colors = colors,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(nombre, { nombre = it }, label = { Text("Nombre") }, colors = colors, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(descripcion, { descripcion = it }, label = { Text("Descripción") }, colors = colors, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(fecha, { fecha = it }, label = { Text("Fecha de lanzamiento") }, colors = colors, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(desarrollador, { desarrollador = it }, label = { Text("Desarrollador") }, colors = colors, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(logros, { logros = it }, label = { Text("Logros totales") }, colors = colors, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(rutaImg, { rutaImg = it }, label = { Text("Ruta imagen") }, colors = colors, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(rutaImgGrande, { rutaImgGrande = it }, label = { Text("Ruta imagen grande") }, colors = colors, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(idCategoria, { idCategoria = it }, label = { Text("ID categoría") }, colors = colors, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(precio, { precio = it }, label = { Text("Precio") }, colors = colors, modifier = Modifier.fillMaxWidth())
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onSave(
                    Videojuego(
                        id = videojuego?.id ?: 0,
                        nombre = nombre.text,
                        descripcion = descripcion.text,
                        fechaLanzamiento = fecha.text,
                        desarrollador = desarrollador.text,
                        logrosTotales = logros.text.toIntOrNull() ?: 0,
                        rutaImagen = rutaImg.text,
                        rutaImagenGrande = rutaImgGrande.text,
                        idCategoria = idCategoria.text.toIntOrNull() ?: 1,
                        precio = precio.text,
                        nombreCategoria = videojuego?.nombreCategoria ?: "",
                        activo = videojuego?.activo ?: 1
                    )
                )
            }) {
                Text("Guardar", color = verde)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancelar", color = blanco) }
        }
    )

}
