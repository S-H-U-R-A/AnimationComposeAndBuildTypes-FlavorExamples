package com.sergio.rodriguez.testanimation.animaciones

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sergio.rodriguez.testanimation.ui.theme.TestAnimationTheme

enum class BoxColorState { //Se definen los estados del cuadro
    Primary,
    BackGround
}

@Composable
fun AnimatedUpdateTransitionBasic(){

    var state by remember { mutableStateOf(BoxColorState.Primary) } //Estado para controlar la animación

    var transition = updateTransition(state, label = "BoxTransition") //Crear una transición para el estado y la configura con el estado creado

    val color by transition.animateColor(
        transitionSpec = {
            when{
                BoxColorState.BackGround isTransitioningTo BoxColorState.Primary -> //Si el estado está cambiando de BackGround a Primary
                    spring(stiffness = 50f)// <- Acá usamos esta animación.
                else ->// <- Si la animación es de primary a background
                    tween(durationMillis = 1000) // <- Acá usamos la animación Tween
            }
        }, label = ""
    ) { state ->
        when (state) {
            BoxColorState.Primary -> MaterialTheme.colorScheme.primary
            BoxColorState.BackGround -> MaterialTheme.colorScheme.background
        }
    }

    // Crear un cuadro (Box) que se animará cuando se haga clic
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color)
            .clickable {
                state = when (state) {//Se cambia el estado del cuadro
                    BoxColorState.Primary -> BoxColorState.BackGround
                    BoxColorState.BackGround -> BoxColorState.Primary
                }
            }
    )

}


enum class BoxState {
    Small,
    Large
}

@Composable
fun AnimatedUpdateTransitionImmediately(){
    var state = remember { MutableTransitionState(BoxState.Small) }.apply {//Estado para controlar la animación
        targetState = BoxState.Large
    }

    var transition = updateTransition( state, label = "BoxTransitionVisibility")//Crear una transición para el estado y la configura con el estado creado

    val size by transition.animateDp(
        label = "",
        targetValueByState = { state ->
            when (state) {
                BoxState.Small -> 100.dp
                BoxState.Large -> 200.dp
            }
        },
        transitionSpec = {
            tween(durationMillis = 3000)// <- Acá usamos la animación Tween
        }
    )

    //Esta caja se animará apenas carga la pantalla
    Box(
        modifier = Modifier
            .background(Color.Cyan)
            .size(size)
    )
}


/**
 * Animated update transition with animated visibility or animated content
 *
 * @param name
 * @param modifier
 */
@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AnimatedUpdateTransitionWithAnimatedVisibilityOrAnimatedContent(
    name: String,
    modifier: Modifier = Modifier
){
    var selected by remember { mutableStateOf(false) }//Estado para controlar la animación

    val transition = updateTransition(selected, label = "")//Creamos el objeto transition

    val borderColor by transition.animateColor(label = "") { isSelected ->//Definimos que se va ha hacer en la animación.
        if (isSelected) Color.Cyan else Color.White
    }

    val elevation by transition.animateDp(label = "") { isSelected ->//Definimos que se va ha hacer en la animación.
        if (isSelected) 10.dp else 2.dp
    }

    TestAnimationTheme {
        Surface(
            onClick = { selected = !selected },// <- Acá cambiamos el estado.
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(2.dp, borderColor),
            shadowElevation = elevation, // <- Aca usamos la animación de la elevación el contenedor surface.
        ) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)) {

                Text(text = "Hello, world!")

                transition.AnimatedVisibility(//Aca definimos una animación de visibilidad que va a usar este elemento.
                    visible = { targetSelected -> targetSelected },
                    enter = expandVertically(),
                    exit = shrinkVertically()
                ) {
                    Text(text = "Todo bien hoy")
                }

                // AnimatedContent as a part of the transition.
                transition.AnimatedContent { targetState ->// <- Acá definimos una animación de cambio de contenido que va a usar este elemento.
                    if (targetState) {
                        Text(text = "Selected")
                    } else {
                        Icon(imageVector = Icons.Default.Phone, contentDescription = "Phone")
                    }
                }
            }
        }
    }
}


enum class BoxState2 {
    Collapsed,
    Expanded
}


@Composable
fun AnimatedReuseAnimationWithObjectTransition() {
    var state by remember {
        mutableStateOf(BoxState2.Collapsed)
    }

    val transitionData = updateTransitionData(state)

    Column {
        Box(
            modifier = Modifier
                .background(transitionData.color)
                .size(transitionData.size)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                state = when(state) {
                    BoxState2.Collapsed -> BoxState2.Expanded
                    BoxState2.Expanded -> BoxState2.Collapsed
                }
            }
        ) {
            Text(text = "Click me")
        }
    }
}


@Composable
private fun updateTransitionData(
    boxState2: BoxState2// Estado que usarán las animaciones
): TransitionData {

    val transition = updateTransition(boxState2, label = "")//Creamos el objeto transition

    val color = transition.animateColor(label = "") { state ->//Se define la primera transición que hará un cambio de color.
        when (state) {
            BoxState2.Collapsed -> Color.Gray
            BoxState2.Expanded -> Color.Red
        }
    }

    val size = transition.animateDp(label = "") { state ->//Se define la segunda transición que hará un cambio de tamaño.
        when (state) {
            BoxState2.Collapsed -> 64.dp
            BoxState2.Expanded -> 128.dp
        }
    }

    return remember(transition) { //Recordamos el objeto Transition para que no se vuelva a crear cada vez que se llame a esta función.
        TransitionData(color, size) //Retornamos el objeto TransitionData, que contiene la información de las animaciones.
    }
}

private class TransitionData(//Clase que nos sirve para almacenar varias animaciones indicandole el tipo de dato que va a usar
    color: State<Color>,//En este caso guardará una animación de color
    size: State<Dp>//En este caso guardará una animación de tamaño
) {
    val color by color
    val size by size
}