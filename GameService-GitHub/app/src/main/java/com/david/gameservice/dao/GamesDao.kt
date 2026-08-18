package com.david.gameservice.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface GamesDao {
    @Insert
    suspend fun insertGame(game: Videojuego)

    @Update
    suspend fun updateGame(game: Videojuego)

    @Delete
    suspend fun deleteGame(game: Videojuego)

    @Query("SELECT * FROM videojuegos")
    fun getAllGames(): Flow<List<Videojuego>>

}
