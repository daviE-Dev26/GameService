package com.david.gameservice.network

import com.david.gameservice.models.GameLocation
import retrofit2.http.GET

interface ApiLocationService {
    @GET("ubicaciones/listar_ubicaciones.php")
    suspend fun obtenerUbicaciones(): List<GameLocation>
}

object RetrofitClientLocations {
    val api: ApiLocationService by lazy {
        RetrofitClient.getRetrofitInstance().create(ApiLocationService::class.java)
    }
}