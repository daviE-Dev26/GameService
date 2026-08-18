package com.david.gameservice.dao

import android.content.Context
import androidx.room.Room

object DatabaseProvider {
    private var INSTANCE: GamesDatabase? = null

    fun getDatabase(context: Context): GamesDatabase {
        return INSTANCE ?: synchronized(this) {
            val newInstance = Room.databaseBuilder(
                context.applicationContext,
                GamesDatabase::class.java,
                "games_database"
            ).build()
            INSTANCE = newInstance
            newInstance
        }
    }
}
