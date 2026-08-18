package com.david.gameservice.models


data class UsuarioResponse(
    val success: Boolean,
    val mensaje: String? = null,
    val usuario: Usuario? = null
)
