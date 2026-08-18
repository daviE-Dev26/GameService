package com.david.gameservice.network

import com.david.gameservice.models.Usuario
import retrofit2.http.GET

interface ApiUsuario {
    @GET("usuario/listar_usuarios.php")
    suspend fun obtenerUsuarios(): List<Usuario>
}

object RetrofitClientUsuario {
    val api: ApiUsuario by lazy {
        RetrofitClient.getRetrofitInstance().create(ApiUsuario::class.java)
    }
}
