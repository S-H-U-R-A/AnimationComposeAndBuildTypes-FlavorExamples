package com.sergio.rodriguez.testanimation.animaciones_de_alto_nivel.animaciones_sin_composables_de_animacion

import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sergio.rodriguez.testanimation.ui.theme.TestAnimationTheme

// LAS ANIMACIONES CREADAS A PARTIR DE USAR EL MÉTODO UPDATE TRANSITION PARA CREAR UN OBJETO TRANSITION
// SON SIMILARES A CUANDO TRABAJABAMOS CON ESTE MISMO OBJETO DENTRO DE ANIMACIONES DE VISIBILIDAD O CONTENIDO
// SOLO QUE PARA ESTE MÉTODO DEBEMOS PROVEERLES UN ESTADO CON EL CUAL TRABAJAR, EN LAS OTRAS ANIMACIONES
// MENCIONADAS EN SU LAMBDA TENEMOS ACCESO AL ESTADO DE LA ANIMACIÓN Y AL OBJETO TRANSITION.
//
// ENTONCES EN RESUMEN: USANDO ESTA FORMA PODEMOS CREAR ANIMACIONES IGUALES A LAS QUE YA EXISTEN DE ALTO NIVEL,
// PERO DE UNA FORMA MÁS MANUAL.

enum class BoxState {//ACÁ DEFINIMOS LOS ESTADO PERSONALIZADOS CON LOS QUE VA A TRABAJAR LA ANIMACIÓN.
    Small,
    Large
}

@Composable
fun MiAnimacionDeTamanioInmediataUsandoUpdateTransition(
    modifier: Modifier = Modifier
){
    val boxSizeState: MutableTransitionState<BoxState> = remember { MutableTransitionState(initialState = BoxState.Small) }.apply {//SE CREA Y SE RECUERDA EL ESTADO CON SU VALOR INICIAL.
        targetState = BoxState.Large//INMEDIATAMENTE CUANDO SE CREA CAMBIAMOS EL ESTADO OBJETIVO QUE EN PRINCIPIO ERA EL MISMO INICIAL, POR ENDE SE DISPARA LA ANIMACIÓN EN AUTOMÁTICO.
    }

    val transition = updateTransition(transitionState = boxSizeState, label = "Animación de tamaño usando el objeto Transition")//ACÁ SE CREA UN OBJETO TRANSITION Y SE LE CONFIGURA UN ESTADO.

    val sizeDp: Dp by transition.animateDp(//CREAMOS UNA ANIMACIÓN PARA EL TAMAÑO DE DP MUY SIMILAR A USAR EL COMPOSABLE [AnimatedDpAsState()].
        transitionSpec = {
            tween(durationMillis = 5000)//ACÁ USAMOS UNA TRANSICIÓN COMPLEJA.
        },
        label = "Animación de tamaño con base en DP",
        targetValueByState = { sizeState: BoxState ->//ACÁ TENEMOS ACCESO AL ESTADO CON EL QUE ESTÁ TRABAJANDO EL OBJETO Transition.
            when (sizeState) {//DEPENDIENDO DEL ESTADO DE LA ANIMACIÓN DEVOLVEMOS UN TAMAÑO
                BoxState.Small -> 100.dp
                BoxState.Large -> 200.dp
            }
        },
    )

    Box(
        modifier = Modifier
            .background(Color.Cyan)
            .size(size = sizeDp)//PARA EL TAMAÑO DE ESTE COMPOSABLE USAMOS EL VALOR DE TAMAÑO DEVUELTO EN LA ANIMACIÓN
    )
}


@Preview(
    showSystemUi = true
)
@Composable
fun MisEjemplosDeAnimacionesBasicasDeTamanioConUpdateTransition(){
    TestAnimationTheme {
        Surface {
            MiAnimacionDeTamanioInmediataUsandoUpdateTransition()
        }
    }
}