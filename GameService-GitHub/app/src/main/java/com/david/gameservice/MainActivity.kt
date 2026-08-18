package com.david.gameservice

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.lifecycleScope
import com.david.gameservice.data.DataStoreManager
import com.david.gameservice.ui.screens.HomeScreen
import com.david.gameservice.ui.IntroScreen
import com.david.gameservice.ui.LoginScreen
import com.david.gameservice.ui.screens.DesarrolladorActivity
import com.david.gameservice.ui.theme.GameServiceTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var dataStoreManager: DataStoreManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataStoreManager = DataStoreManager(this)

        setContent {
            val introShown by dataStoreManager.introShownFlow.collectAsState(initial = false)

            var showIntro by remember { mutableStateOf(!introShown) }
            var showLogin by remember { mutableStateOf(false) }
            var showHome by remember { mutableStateOf(false) }
            var loggedUsername by remember { mutableStateOf("") }

            GameServiceTheme {
                when {
                    showIntro -> IntroScreen(
                        onFinish = {
                            showIntro = false
                            showLogin = true
                            lifecycleScope.launch {
                                dataStoreManager.setIntroShown(true)
                            }
                        },
                        dataStoreManager = dataStoreManager
                    )
                    showHome -> HomeScreen(
                        username = loggedUsername,
                        dataStoreManager = dataStoreManager,
                        onLogout = {
                            loggedUsername = ""
                            showHome = false
                            showLogin = true
                        }
                    )

                    else -> LoginScreen(
                        onLoginSuccess = { username ->
                            loggedUsername = username
                            showHome = true
                        },
                        dataStoreManager = dataStoreManager
                    )
                }
            }
        }
    }
}
