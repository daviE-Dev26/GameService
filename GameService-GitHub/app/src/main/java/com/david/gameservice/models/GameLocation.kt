package com.david.gameservice.models

data class GameLocation(
    val id: Int,
    val nombre: String,
    val latitud: Double,
    val longitud: Double,
    val imagen: String,
    val descripcion: String
)

