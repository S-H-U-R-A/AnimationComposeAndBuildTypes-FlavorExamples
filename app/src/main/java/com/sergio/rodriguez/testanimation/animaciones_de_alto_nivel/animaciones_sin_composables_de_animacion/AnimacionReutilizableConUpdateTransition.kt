package com.sergio.rodriguez.testanimation.animaciones_de_alto_nivel.animaciones_sin_composables_de_animacion

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sergio.rodriguez.testanimation.ui.theme.TestAnimationTheme

enum class StateCaja {//ACÁ DEFINIMOS LOS ESTADOS PERSONALIZADOS CON LOS QUE VA A TRABAJAR LA ANIMACIÓN EN EL COMPOSABLE QUE USA LA ANIMACIÓN.
    COLLAPSED,
    EXPANDED
}

private class TransitionData(//CLASE PRIVADA PARA ALMACENAR PROPIEDADES DINÁMICAS QUE REPRESENTAN ANIMACIONES.
    color: State<Color>,//ACÁ ESTAMOS RECIBIENTO UN ARGUMENTO DE COLOR QUE SE USARÁ APARA ANIMAR UN COMPOSABLE.
    size: State<Dp>//ACÁ ESTAMOS RECIBIENTO UN ARGUMENTO DE TAMAÑO QUE SE USARÁ APARA ANIMAR UN COMPOSABLE.
) {
    val color: Color by color//USAMOS EL DELEGADO DE LAS PROPIEDADES PARA CCEDER AL MÉTODO GETTER DEL ARGUMENTO ENVIADO.
    val size: Dp by size
}

@Composable
private fun updateTransitionData(//MÉTODO COMPOSABLE QUE RETORNA Y SIRVE PARA SER USADO DENTRO DE UN MÉTODO COMPOSABLE
    stateBox: StateCaja//ESTADO QUE USARÁ EL OBJETO Transition PARA DEFINIR LAS ANIMACIONES
): TransitionData {

    val transition: Transition<StateCaja> = updateTransition(//ACÁ SE CREA UN OBJETO TRANSITION Y SE LE CONFIGURA EL ESTADO PASADO COMO ARGUMENTO.
        targetState = stateBox,
        label = "Objeto transition principal"
    )

    val color = transition.animateColor(
        label = "Animación de color con base en el estado del objeto Transition"
    ) { stateBox: StateCaja ->//SE DEFINE UNA ANIMACIÓN DE CAMBIO DE COLOR DINÁMICO.
        when (stateBox) {
            StateCaja.COLLAPSED -> Color.Gray
            StateCaja.EXPANDED -> Color.Red
        }
    }

    val size = transition.animateDp(
        label = "Animación de tamaño con base en el estado del objeto Transition"
    ) { stateBox: StateCaja ->//SE DEFINE UNA ANIMACIÓN DE CAMBIO DE TAMAÑO DINÁMICO.
        when (stateBox) {
            StateCaja.COLLAPSED -> 64.dp
            StateCaja.EXPANDED -> 128.dp
        }
    }

    return remember(key1 = transition) {//CON EL USO DE REMEMBER, ACÁ INDICAMOS QUE A MENOS QUE CAMBIE EL VALOR DEL ARGUMENTO NO SE EJECUTARÁ EL CAMBIO, MUY PARECIDO A LOS EFECTO SECUNDARIOS.
        TransitionData(color, size)//CREAMOS Y RECORDAMOS NUESTRO OBJETO QUE ALMACENA LOS VALORES DINAMICOS DE LAS ANIMACIONE.
    }
}


@Composable
fun AnimatedReuseAnimationWithObjectTransition(
    modifier: Modifier = Modifier
) {
    var stateBox: StateCaja by remember { mutableStateOf(value = StateCaja.COLLAPSED) }//SE CREA Y SE RECUERDA EL ESTADO CON SU VALOR INICIAL.

    val transitionData: TransitionData = updateTransitionData(stateBox = stateBox)//USAMOS EL MÉTODO QUE RETORNA EL OBJETO QUE CONTIENE ANIMACIONES.

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize().padding(all = 16.dp)
    ) {
        Box(
            modifier = Modifier
                .background(color = transitionData.color)//A PARTIR DEL OBJETO DE ANIMACIONES USAMOS LA ANIMACIÓN QUE NECESITEMOS.
                .size(size = transitionData.size)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {//EVENTO QUE CAMBIA EL ESTADO PRINCIPAL DE LA ANIMACIÓN.
                stateBox = when(stateBox) {
                    StateCaja.COLLAPSED -> StateCaja.EXPANDED
                    StateCaja.EXPANDED -> StateCaja.COLLAPSED
                }
            }
        ) {
            Text(text = "Pulsame para cambiar el tamaño y color de la caja")
        }
    }
}


@Preview(
    showSystemUi = true
)
@Composable
fun MisEjemplosDeAnimacionesConUpdateTransition(){
    TestAnimationTheme {
        Surface {
            AnimatedReuseAnimationWithObjectTransition()
        }
    }
}