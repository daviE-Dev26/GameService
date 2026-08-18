package com.david.gameservice.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.david.gameservice.R
import com.david.gameservice.data.DataStoreManager
import com.david.gameservice.ui.theme.color5
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun IntroScreen(onFinish: () -> Unit, dataStoreManager: DataStoreManager) {
    var page by remember { mutableStateOf(0) }

    val titles = listOf(
        stringResource(R.string.title1),
        stringResource(R.string.title2),
        stringResource(R.string.title3)
    )

    val phrases = listOf(
        stringResource(R.string.frase1),
        stringResource(R.string.frase2),
        stringResource(R.string.frase3)
    )

    val buttons = listOf(
        stringResource(R.string.siguiente),
        stringResource(R.string.siguiente),
        stringResource(R.string.iniciar)
    )

    val images = listOf(
        R.drawable.image01,
        R.drawable.image02,
        R.drawable.image03
    )

    // Espacios ajustables vía dimens
    val topPadding = dimensionResource(R.dimen.espacio_8)
    val titlePhraseSpacing = dimensionResource(R.dimen.espacio_4)
    val phraseImageSpacing = dimensionResource(R.dimen.espacio_1)
    val imageButtonSpacing = dimensionResource(R.dimen.espacio_1) // ahora controlable
    val buttonBottomPadding = dimensionResource(R.dimen.espacio_1)
    val phraseHorizontalPadding = dimensionResource(R.dimen.espacio_8)

    Surface(modifier = Modifier.fillMaxSize(), color = Color.Black) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = dimensionResource(R.dimen.espacio_3))
                .padding(vertical = dimensionResource(R.dimen.espacio_4)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top // aquí ya no fuerza espacio
        ) {
            // Título
            Text(
                text = titles[page],
                color = Color.White,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = topPadding)
            )

            Spacer(modifier = Modifier.height(titlePhraseSpacing))

            // Frase
            Text(
                text = phrases[page],
                color = Color(0xFFBFBFBF),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = phraseHorizontalPadding)
            )

            Spacer(modifier = Modifier.height(phraseImageSpacing))

            // Imagen
            Image(
                painter = painterResource(images[page]),
                contentDescription = stringResource(R.string.logo),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp)
            )

            Spacer(modifier = Modifier.height(imageButtonSpacing)) // ahora controlable

            // Botón
            Button(
                onClick = {
                    if (page < titles.size - 1) {
                        page++
                    } else {
                        CoroutineScope(Dispatchers.IO).launch {
                            dataStoreManager.setIntroShown(true)
                        }
                        onFinish()
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = color5,
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth(0.82f)
                    .padding(bottom = buttonBottomPadding)
                    .height(56.dp)
            ) {
                Text(
                    text = buttons[page],
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}
