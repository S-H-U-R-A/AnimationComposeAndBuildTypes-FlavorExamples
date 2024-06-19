package com.sergio.rodriguez.testanimation.animaciones_de_alto_nivel.animaciones_sin_composables_de_animacion

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.InfiniteTransition
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.sergio.rodriguez.testanimation.ui.theme.TestAnimationTheme

@Composable
fun MiAnimacionInfinitaDeColorInfiniteColor(
    modifier: Modifier = Modifier
){
    val infiniteTransition: InfiniteTransition = rememberInfiniteTransition(label = "Animación infinita")//ACÁ SE CREA UN OBJETO INFINITETRANSITION, NO REQUIERE ESTADO POR LO QUE ES INFINITA.

    val color: Color by infiniteTransition.animateColor(//ACÁ DEFINIMOS UNA ANIMACIÓN DE COLOR INFINITA ENTRE EL COLOR ROJO Y VERDE
        initialValue = Color.Red,
        targetValue = Color.Green,
        animationSpec = infiniteRepeatable(//ACÁ USAMOS UNA TRANSICIÓN INFINITA COMPLEJA.
            animation = tween(durationMillis = 1000, easing = LinearEasing),//INDICAMOS CUAL VA SER LA TRANSICIÓN COMPLEJA HA HACER.
            repeatMode = RepeatMode.Reverse//INDICAMOS EL MODO DE REPETICIÓN DE LA ANIMACIÓN, EN ESTE CASO IRÁ DE COLOR VERDE A ROJO INICIALMENTE.
        ),
        label = "Animación de color infinita"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = color)//APLICAMOS EL COLOR ANIMADO A ESTA CAJA.
    )
}

@Preview(
    showSystemUi = true
)
@Composable
fun MiEjemplosDeAnimacionInfinitaConRememberTransitionPreview(){
    TestAnimationTheme {
        Surface {
            MiAnimacionInfinitaDeColorInfiniteColor()
        }
    }
}