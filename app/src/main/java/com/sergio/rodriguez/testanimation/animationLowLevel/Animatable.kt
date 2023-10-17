package com.sergio.rodriguez.testanimation.animationLowLevel


import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ExampleAnimatableBasic(modifier: Modifier = Modifier){

/*    val offsetX = remember { Animatable(0f) }
    val offsetY = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        offsetX.animateTo(100f, animationSpec = tween(1000))
        offsetY.animateTo(100f, animationSpec = tween(1000))
    }

// En el composable
    Box(
        Modifier
            .offset {
                IntOffset(offsetX.value.roundToInt(), offsetY.value.roundToInt())
            }
    ) {
        Text("Hello world my stupid wife")
    }*/

    var ok by remember { mutableStateOf(false) }//Estado de la animación.

    val color = remember { Animatable( Color.Gray ) }//Animatable es una clase que permite animar un valor mutable de un tipo dado.

    LaunchedEffect(ok) {//Composable apropiado para unirse a un alcance de corrutina y ejecutar un efecto secundario
        color.animateTo(//Acá se indica cual es el valor objetivo al cual queremos llegar.
            targetValue =  if(ok) Color.Green else Color.Red,
            animationSpec = tween(5000)//Se puede agregar un efecto para la transición.
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(
                    color = color.value//Acá usamos el valor del animatable para cambiar el color de fondo.
                )
        )
        Spacer(
            modifier = Modifier
                .height(20.dp)
        )
        Button(
            onClick = {//Evento para cambiar el estado de la animación.
                ok = !ok
            }
        ) {
            Text("Click me")
        }
    }
}