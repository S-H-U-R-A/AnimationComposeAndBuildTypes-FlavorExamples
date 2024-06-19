package com.sergio.rodriguez.testanimation.animaciones_de_alto_nivel

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sergio.rodriguez.testanimation.ui.theme.TestAnimationTheme

enum class Page {
    PAGE_A,
    PAGE_B
}

/**
 * Mi animacion de contenido difuminado crossfade
 *
 * ESTE TIPO DE ANIMACIÓN ES MUY PARECIDA A LA ANIMACIÓN DE CONTENIDO CON
 * UN ADICIONAL DE UN DIFUMINADO CUANDO SE CAMBIA EL CONTENIDO
 *
 */
@Composable
fun MiAnimacionDeContenidoDifuminadoCrossfade(
    name: String,
    modifier: Modifier = Modifier
) {
    var currentPage: Page by remember { mutableStateOf(Page.PAGE_A) }//ESTADO INICIAL QUE SE VA A ESCUCHAR.

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxSize()
            .padding(all = 16.dp)
    ) {
        Row {
            Button(onClick = { currentPage = Page.PAGE_A }) {//EVENTO QUE CAMBIARÁ EL VALOR DEL ESTADO.
                Text("Page A")
            }

            Spacer(modifier = Modifier.weight(weight = 1f))

            Button(onClick = { currentPage = Page.PAGE_B }) {//EVENTO QUE CAMBIARÁ EL VALOR DEL ESTADO.
                Text("Page B")
            }
        }

        Spacer(modifier = Modifier.height(height = 32.dp))

        Crossfade(
            targetState = currentPage,
            animationSpec = spring(),//TRANSICIÓN FISICA MÁS AVANZADA
            label = "Mi animación de contenido difuminado"
        ) { stateScreen: Page ->//ESTA ANIMACIÓN EN LAMBDA NOS DA EL VALOR DEL ESTADO OBJETIVO.
            when (stateScreen) {
                Page.PAGE_A -> {
                    Text(
                        color = Color.White,
                        text = "Page A",
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = Color.DarkGray)
                            .border(width = 1.dp, color = Color.Red)
                    )
                }
                Page.PAGE_B -> {
                    Text(
                        color = Color.White,
                        text = "Page B",
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = Color.LightGray)
                            .border(width = 1.dp, color = Color.Red)
                    )
                }
            }
        }
    }
}


@Preview(
    showSystemUi = true
)
@Composable
fun MiEjemplodeAnimacionDeContenidoDifuminado(){
    TestAnimationTheme {
        Surface {
            MiAnimacionDeContenidoDifuminadoCrossfade(name = "Pulsame")
        }
    }
}

