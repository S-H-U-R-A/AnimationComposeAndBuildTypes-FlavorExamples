package com.sergio.rodriguez.testanimation.animaciones

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun InfiniteColorAnimation(){
    val infiniteTransition = rememberInfiniteTransition(label = "")//Se crea el objeto de transición infinita

    val color by infiniteTransition.animateColor(//Se define que va a hacer la animación, en este caso un cambio entre colores Rojo y verde
        initialValue = Color.Red,
        targetValue = Color.Green,
        animationSpec = infiniteRepeatable(//Especificamos el efecto de la animación.
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse//Acá indicamos una animación infinita empezando desde el color verde al rojo y viceversa
        ),
        label = ""
    )

    Box(
        Modifier.fillMaxSize().background(color)//Aplicamos la animación para el fondo de la caja.
    )
}