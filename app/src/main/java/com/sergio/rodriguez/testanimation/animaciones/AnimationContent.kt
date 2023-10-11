package com.sergio.rodriguez.testanimation.animaciones

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.sergio.rodriguez.testanimation.R

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimateContentBasic(
    name: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(16.dp)
    ) {
        var count by remember {
            mutableStateOf(0)
        }
        Button(
            onClick = {
                count++
            }
        ) {
            Text(text = "add")
        }
        Spacer(modifier = Modifier.weight(1f))
        AnimatedContent(
            targetState = count,
            label = ""
        ) { targetCount ->
            Text(text = "Count: $targetCount")
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimateContentCustom(
    name: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(16.dp)
    ) {
        var count by remember {
            mutableStateOf(0)
        }
        Button(
            onClick = {
                count++
            }
        ) {
            Text(text = "add")
        }
        Button(
            onClick = {
                count--
            }
        ) {
            Text(text = "Minus")
        }
        Spacer(modifier = Modifier.weight(1f))
        AnimatedContent(
            targetState = count,
            label = "",
            transitionSpec = {
                //Usamos el valor del estado para hacer validaciones.
                if (targetState > initialState) { //Comparamos el número entrante con el número anterior.
                    slideInVertically { height -> height } + fadeIn() with
                            slideOutVertically { height -> - height } + fadeOut()
                } else {
                    slideInVertically { height -> -height } + fadeIn() with
                            slideOutVertically { height -> height } + fadeOut()
                }.using(
                    SizeTransform(clip = false)
                )
            }
        ) { targetCount ->
            Text(text = "Count: $targetCount")
        }
    }
}

@OptIn(
    ExperimentalAnimationApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun AnimateContentCustomAdvance(
    name: String,
    modifier: Modifier = Modifier
) {
    var expanded by remember {
        mutableStateOf(false)//Estado que permite hacer validaciones dentro del composable que sirve de envoltorio para el contenido animado.
    }
    Surface(
        color = MaterialTheme.colorScheme.onSecondary,
        onClick = { expanded = !expanded }//Evento que cambia el estado que usa el composable que sirve de envoltorio para el contenido animado.
    ) {
        AnimatedContent(
            targetState = expanded,
            label = "",
            transitionSpec = {
                fadeIn(animationSpec = tween(150, 150)) with
                        fadeOut(animationSpec = tween(150)) using
                        SizeTransform { initialSize, targetSize ->
                            if (targetState) {//Si el estado es true se anima con el tamaño del composable que corresponde para este caso.
                                keyframes {
                                    // Se expande horizontalmente primero.
                                    IntSize(targetSize.width, initialSize.height) at 150 //Indica que a los 150 milisegundos se ejecute la animación.
                                    durationMillis = 300
                                }
                            } else {//Si el estado es false se anima con el tamaño del composable que corresponde para este caso.
                                keyframes {
                                    // De lo contrario primero se contrae verticalmente.
                                    IntSize(initialSize.width, targetSize.height) at 150 //Indica que a los 150 milisegundos se ejecute la animación.
                                    durationMillis = 300
                                }
                            }
                        }
            }
        ) {targetExpanded ->
            if (targetExpanded) {
                Expanded()
            } else {
                ContentIcon()
            }
        }
    }
}

@Composable
fun Expanded() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(Color.Blue) // Cambia el color de fondo según tus necesidades
    ) {
        Text(
            text = "lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
            color = Color.White, // Cambia el color del texto según tus necesidades
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun ContentIcon() {
    Box(modifier = Modifier.height(48.dp)){
        Icon(
            imageVector = Icons.Default.Call, // Cambia el ícono según tus necesidades
            contentDescription = "Icono de Contenido",
            tint = Color.Black, // Cambia el color del ícono según tus necesidades
            modifier = Modifier
                .size(48.dp) // Cambia el tamaño del ícono según tus necesidades
        )
    }

}

