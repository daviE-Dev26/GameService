package com.david.gameservice.network

import com.david.gameservice.models.Videojuego
import com.david.gameservice.utils.API_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface VideojuegoApi {
    @GET("videojuego/listar_videojuego.php")
    suspend fun obtenerVideojuegos(): List<Videojuego>
}

object RetrofitClientVideojuego {
    val api: VideojuegoApi by lazy {
        Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(VideojuegoApi::class.java)
    }
}
