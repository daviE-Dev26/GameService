package com.david.gameservice.utils

import com.david.gameservice.models.UsuarioResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface UsuarioService {
    @FormUrlEncoded
    @POST("usuario/login_usuario.php")
    suspend fun login(
        @Field("correo") correo: String,
        @Field("clave") clave: String
    ): UsuarioResponse
}

object RetrofitClientUsuario {
    val api: UsuarioService by lazy {
        Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UsuarioService::class.java)
    }
}
