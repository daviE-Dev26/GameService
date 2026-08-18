package com.david.gameservice.ui.screens

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.david.gameservice.ui.theme.GameServiceTheme
import com.david.gameservice.ui.theme.color1
import com.david.gameservice.ui.theme.color3
import com.david.gameservice.ui.theme.color4
import com.david.gameservice.ui.theme.color5
import com.david.gameservice.utils.GamesActivity

class DesarrolladorActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GameServiceTheme {
                DesarrolladorScreen(
                    onVideojuegoClick = {
                        startActivity(Intent(this, GamesActivity::class.java))
                    },
                    onCategoriaClick = {
                        //startActivity(Intent(this, CategoriaCrudActivity::class.java))
                    },
                    onLogrosClick = {
                        //startActivity(Intent(this, LogroCrudActivity::class.java))
                    }
                )
            }
        }
    }
}

@Composable
fun DesarrolladorScreen(
    onVideojuegoClick: () -> Unit,
    onCategoriaClick: () -> Unit,
    onLogrosClick: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = color3 // fondo negro
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 40.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Título
            Text(
                text = "Panel del Desarrollador",
                color = color1,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            // Botones principales
            Column(
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DeveloperButton(
                    text = "Videojuegos",
                    background = color5,
                    textColor = color3,
                    onClick = onVideojuegoClick
                )
                DeveloperButton(
                    text = "Categorías",
                    background = color4,
                    textColor = color3,
                    onClick = onCategoriaClick
                )
                DeveloperButton(
                    text = "Logros",
                    background = Color(0xFF3A3A3A),
                    textColor = color1,
                    onClick = onLogrosClick
                )
            }

            // Pie de página o crédito
            Text(
                text = "GameService Dev Tools",
                color = Color.Gray,
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 20.dp)
            )
        }
    }
}

@Composable
fun DeveloperButton(
    text: String,
    background: Color,
    textColor: Color,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .shadow(8.dp, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = background)
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
