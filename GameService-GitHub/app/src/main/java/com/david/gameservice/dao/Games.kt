package com.david.gameservice.dao

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.util.Date

@Entity(tableName = "videojuegos")
data class Videojuego(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val desarrollador: String,
    val precio: String,
    val categoria: String,
    val descripcion: String,
)