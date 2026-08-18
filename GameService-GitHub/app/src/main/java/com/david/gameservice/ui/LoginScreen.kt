package com.david.gameservice.ui

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.david.gameservice.R
import com.david.gameservice.data.DataStoreManager
import com.david.gameservice.ui.screens.DesarrolladorActivity
import com.david.gameservice.ui.theme.color1
import com.david.gameservice.ui.theme.color2
import com.david.gameservice.ui.theme.color3
import com.david.gameservice.ui.theme.color4
import com.david.gameservice.ui.theme.color5
import com.david.gameservice.utils.RetrofitClientUsuario
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    onLoginSuccess: (username: String) -> Unit,
    dataStoreManager: DataStoreManager
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    val paddingHorizontal = dimensionResource(R.dimen.espacio_3)
    val paddingVertical = dimensionResource(R.dimen.espacio_4)
    val scope = rememberCoroutineScope()
    val invitadoText = stringResource(R.string.invitado)
    val context = LocalContext.current // Necesario para abrir la actividad

    Surface(modifier = Modifier.fillMaxSize(), color = color3) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = paddingHorizontal, vertical = paddingVertical),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.login_title),
                style = MaterialTheme.typography.headlineLarge,
                color = color1
            )

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.espacio_6)))

            // Campo correo
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(stringResource(R.string.correo_label), color = color1) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = color1,
                    unfocusedTextColor = color1,
                    cursorColor = color5,
                    focusedBorderColor = color5,
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = color5,
                    unfocusedLabelColor = Color.LightGray
                )
            )

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.espacio_3)))

            // Campo contraseña
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(stringResource(R.string.password_label), color = color1) },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = color1,
                    unfocusedTextColor = color1,
                    cursorColor = color5,
                    focusedBorderColor = color5,
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = color5,
                    unfocusedLabelColor = Color.LightGray
                )
            )

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.espacio_5)))

            // Botón ingresar
            Button(
                onClick = {
                    scope.launch {
                        errorMessage = ""
                        isLoading = true
                        try {
                            if (email.isBlank() || password.isBlank()) {
                                errorMessage = "Completa todos los campos"
                            } else {
                                val response = RetrofitClientUsuario.api.login(email, password)

                                if (response.success && response.usuario != null) {
                                    val nombreUsuario = response.usuario.nickname
                                    dataStoreManager.setUserLogged(true)
                                    dataStoreManager.setUsername(nombreUsuario)
                                    onLoginSuccess(nombreUsuario)
                                } else {
                                    errorMessage = response.mensaje ?: "Correo o clave incorrectos"
                                }
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                            errorMessage = "Error de conexión o servidor"
                        }
                        isLoading = false
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = color5, contentColor = color3),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = if (isLoading)
                        stringResource(R.string.ingresando)
                    else
                        stringResource(R.string.ingresar)
                )
            }

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.espacio_3)))

            // Entrar como invitado
            TextButton(
                onClick = {
                    scope.launch {
                        dataStoreManager.setUserLogged(false)
                        onLoginSuccess(invitadoText)
                    }
                }
            ) {
                Text(text = stringResource(R.string.entrar_invitado), color = color5)
            }

            // Entrar como desarrollador → abre directamente la actividad
            TextButton(
                onClick = {
                    scope.launch {
                        dataStoreManager.setUserLogged(false)
                        val intent = Intent(context, DesarrolladorActivity::class.java)
                        context.startActivity(intent)
                    }
                }
            ) {
                Text(text = stringResource(R.string.entrar_desarrollador), color = color4)
            }

            // Mostrar error
            if (errorMessage.isNotEmpty()) {
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.espacio_3)))
                Text(text = errorMessage, color = color2)
            }
        }
    }
}
