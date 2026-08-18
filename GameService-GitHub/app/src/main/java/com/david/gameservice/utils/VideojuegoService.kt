package com.david.gameservice.utils

import com.david.gameservice.models.Videojuego
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface VideojuegoService {

    @GET("videojuego/listar_videojuego.php")
    suspend fun obtenerVideojuegos(): List<Videojuego>

    @FormUrlEncoded
    @POST("videojuego/agregar_videojuego.php")
    suspend fun agregarVideojuego(
        @Field("nombre") nombre: String,
        @Field("descripcion") descripcion: String,
        @Field("fecha_lanzamiento") fechaLanzamiento: String,
        @Field("desarrollador") desarrollador: String,
        @Field("logros_totales") logrosTotales: Int,
        @Field("precio") precio: String,
        @Field("ruta_imagen") rutaImagen: String,
        @Field("ruta_imagen_grande") rutaImagenGrande: String,
        @Field("id_categoria") idCategoria: Int
    ): Response<ResponseBody>   // ✅

    @FormUrlEncoded
    @POST("videojuego/actualizar_videojuego.php")
    suspend fun actualizarVideojuego(
        @Field("id_videojuego") id: Int,
        @Field("nombre") nombre: String,
        @Field("descripcion") descripcion: String,
        @Field("fecha_lanzamiento") fechaLanzamiento: String,
        @Field("desarrollador") desarrollador: String,
        @Field("logros_totales") logrosTotales: Int,
        @Field("precio") precio: String,
        @Field("ruta_imagen") rutaImagen: String,
        @Field("ruta_imagen_grande") rutaImagenGrande: String,
        @Field("id_categoria") idCategoria: Int
    ): Response<ResponseBody>   // ✅

    @FormUrlEncoded
    @POST("videojuego/eliminar_videojuego.php")
    suspend fun eliminarVideojuego(
        @Field("id_videojuego") id: Int
    ): Response<ResponseBody>   // ✅
}