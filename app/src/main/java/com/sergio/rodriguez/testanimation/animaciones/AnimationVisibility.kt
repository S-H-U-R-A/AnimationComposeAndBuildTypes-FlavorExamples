package com.sergio.rodriguez.testanimation.animaciones

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterExitState
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

@Composable
fun AnimatedVisibilityBasic(name: String, modifier: Modifier = Modifier) {

    var visible by remember { mutableStateOf(true) }

    Column {
        AnimatedVisibility(visible = visible) {
            Text(text = "Hello $name!", modifier = modifier)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                visible = !visible
            },
            modifier = Modifier
        ) {
            Text(text = name)
        }
    }
}

@Composable
fun AnimatedVisibilityCustom(name: String, modifier: Modifier = Modifier) {

    var visible by remember { mutableStateOf(true) }

    val density = LocalDensity.current

    Column {
        AnimatedVisibility(
            visible = visible,
            enter = slideInHorizontally {
                with(density) { -40.dp.roundToPx() }// Expandirse desde la izquierda.
            } + expandHorizontally(
                // Expand from the top.
                expandFrom = Alignment.Start// Expandirse desde la izquierda.
            ) + fadeIn(
                initialAlpha = 0.3f// Opacidad inicial del 30%
            ),
            exit = slideOutHorizontally() + shrinkHorizontally() + fadeOut()
        ) {
            Text(text = "Hello Darlyn", modifier = modifier)//Contenido que va a ser animado.
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                visible = !visible
            },
            modifier = Modifier
        ) {
            Text(text = name)
        }
    }
}

@Composable
fun AnimatedVisibilityWitLifeCycle(name: String, modifier: Modifier = Modifier) {

    val state: MutableTransitionState<Boolean> = remember {
        MutableTransitionState(false).apply {
            targetState = true //Acá indicamos que la animación se ejecute cuando el composable se crea.
        }
    }

    Column {
        AnimatedVisibility(
            visible = state.targetState,
        ) {
            Text(text = "Hello Darlyn", modifier = modifier)//Contenido que va a ser animado.
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = when {// Acá se muestra el estado de la animación.
                state.isIdle && state.currentState -> "Visible"
                !state.isIdle && state.currentState -> "Disappearing"
                state.isIdle && !state.currentState -> "Invisible"
                else -> "Appearing"
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                state.targetState = !state.targetState
            },
            modifier = Modifier
        ) {
            Text(text = name)
        }

    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedVisibilitySecondaryElements(name: String, modifier: Modifier = Modifier) {

    var visible by remember { mutableStateOf(false) }

    Column {
        AnimatedVisibility(
            visible = visible,
            enter = EnterTransition.None,//Acá indicamos que no queremos animación de entrada.
            exit = ExitTransition.None//Acá indicamos que no queremos animación de salida.
        ) {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(Color.DarkGray)) {
                Box(
                    Modifier
                        .align(Alignment.Center)
                        .animateEnterExit(
                            enter = slideInHorizontally() + fadeIn(),
                            exit = slideOutHorizontally() + fadeOut()
                        )
                        .sizeIn(minWidth = 256.dp, minHeight = 64.dp)
                        .background(Color.Red)
                ) {
                    //Contenido de la notificación personalizada
                    Button(onClick = { visible = !visible }) {
                        Text(text = "Hello Darlyn", modifier = modifier)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                visible = !visible
            },
            modifier = Modifier
        ) {
            Text(text = name)
        }
    }


}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedVisibilitySecondaryElementsCustom(name: String, modifier: Modifier = Modifier) {

    var visible by remember { mutableStateOf(false) }

    Column {
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            //transition es un objeto que nos permite acceder a los estados de la animación y animar ciertos valores. Es parecido a animation*AsState.
            val background by transition.animateColor(label = "") { state -> //Acá se indica que el color de fondo va a cambiar dependiendo del estado de la animación.
                if (state == EnterExitState.Visible) Color.Blue else Color.Gray
            }
            Box(modifier = Modifier.size(128.dp).background(background))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                visible = !visible
            },
            modifier = Modifier
        ) {
            Text(text = name)
        }
    }
}