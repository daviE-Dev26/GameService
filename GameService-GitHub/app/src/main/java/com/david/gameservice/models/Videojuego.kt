package com.david.gameservice.models

import com.google.gson.annotations.SerializedName

data class Videojuego(
    @SerializedName("id_videojuego") val id: Int = 0,
    @SerializedName("nombre") val nombre: String = "",
    @SerializedName("descripcion") val descripcion: String = "",
    @SerializedName("fecha_lanzamiento") val fechaLanzamiento: String = "",
    @SerializedName("desarrollador") val desarrollador: String = "",
    @SerializedName("logros_totales") val logrosTotales: Int = 0,
    @SerializedName("precio") val precio: String = "",
    @SerializedName("ruta_imagen") val rutaImagen: String = "",
    @SerializedName("ruta_imagen_grande") val rutaImagenGrande: String = "",
    @SerializedName("nombre_categoria") val nombreCategoria: String = "",
    @SerializedName("id_categoria") val idCategoria: Int = 1,
    @SerializedName("activo") val activo: Int = 1
)
