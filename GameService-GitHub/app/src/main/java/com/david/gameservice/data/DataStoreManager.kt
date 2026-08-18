package com.david.gameservice.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("settings")

class DataStoreManager(private val context: Context) {

    companion object {
        val INTRO_SHOWN = booleanPreferencesKey("intro_shown")
        val USER_LOGGED = booleanPreferencesKey("user_logged")
        val USERNAME = stringPreferencesKey("username")
    }

    // Flujos
    val introShownFlow: Flow<Boolean> = context.dataStore.data
        .map { preferences -> preferences[INTRO_SHOWN] ?: false }

    val userLoggedFlow: Flow<Boolean> = context.dataStore.data
        .map { preferences -> preferences[USER_LOGGED] ?: false }

    val usernameFlow: Flow<String> = context.dataStore.data
        .map { preferences -> preferences[USERNAME] ?: "Invitado" }

    // Funciones para guardar datos
    suspend fun setIntroShown(shown: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[INTRO_SHOWN] = shown
        }
    }

    suspend fun setUserLogged(logged: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[USER_LOGGED] = logged
        }
    }

    suspend fun setUsername(name: String) {
        context.dataStore.edit { preferences ->
            preferences[USERNAME] = name
        }
    }
}
