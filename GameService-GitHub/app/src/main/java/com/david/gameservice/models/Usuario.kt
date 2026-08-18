package com.david.gameservice.models

import com.google.gson.annotations.SerializedName

data class Usuario(
    @SerializedName("id_usuario") val id: Int,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("firma") val firma: String,
    @SerializedName("correo") val correo: String,
    @SerializedName("ruta_imagen") val rutaImagen: String,
    @SerializedName("fecha_registro") val fechaRegistro: String
)
