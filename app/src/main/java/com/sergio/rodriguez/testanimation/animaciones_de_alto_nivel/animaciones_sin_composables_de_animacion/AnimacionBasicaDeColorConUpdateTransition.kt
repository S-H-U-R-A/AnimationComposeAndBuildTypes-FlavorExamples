package com.sergio.rodriguez.testanimation.animaciones_de_alto_nivel.animaciones_sin_composables_de_animacion

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.sergio.rodriguez.testanimation.ui.theme.TestAnimationTheme

// LAS ANIMACIONES CREADAS A PARTIR DE USAR EL MÉTODO UPDATE TRANSITION PARA CREAR UN OBJETO TRANSITION
// SON SIMILARES A CUANDO TRABAJABAMOS CON ESTE MISMO OBJETO DENTRO DE ANIMACIONES DE VISIBILIDAD O CONTENIDO
// SOLO QUE PARA ESTE MÉTODO DEBEMOS PROVEERLES UN ESTADO CON EL CUAL TRABAJAR, EN LAS OTRAS ANIMACIONES
// MENCIONADAS EN SU LAMBDA TENEMOS ACCESO AL ESTADO DE LA ANIMACIÓN Y AL OBJETO TRANSITION.
//
// ENTONCES EN RESUMEN: USANDO ESTA FORMA PODEMOS CREAR ANIMACIONES IGUALES A LAS QUE YA EXISTEN DE ALTO NIVEL,
// PERO DE UNA FORMA MÁS MANUAL.


enum class BoxColorState {//ACÁ DEFINIMOS LOS ESTADO PERSONALIZADOS CON LOS QUE VA A TRABAJAR LA ANIMACIÓN.
    PRIMARY,
    BACKGROUND
}

@Composable
fun MiAnimacionBasicaDeColorUsandoUpdateTransition(
    modifier: Modifier = Modifier
){
    var state: BoxColorState by remember { mutableStateOf(value= BoxColorState.PRIMARY) }//SE CREA Y SE RECUERDA EL ESTADO.

    val transition: Transition<BoxColorState> = updateTransition(//ESTE MÉTODO PERMITE DEFINIR UN ESTADO OBJETIVO CON EL QUE VA A TRABAJAR EL OBJETO TRANSITION QUE CREA PARA HACER UNA ANIMACIÓN.
        targetState = state,//ACÁ SE CONFIGURA EL ESTADO OBJETIVO PARA EJECUTAR LA ANIMACIÓN.
        label = "BoxTransition"
    )

    val colorAnimado: Color by transition.animateColor(//CREAMOS UNA ANIMACIÓN DE COLOR MUY SIMILAR A USAR EL COMPOSABLE [AnimatedColorAsState()].
        transitionSpec = {//ESTA LAMBDA NO DA ACCESO AL VALOR OBJETIVO DEL ESTADO SI LO QUEREMOS USAR
            when{
                this.targetState isTransitioningTo BoxColorState.PRIMARY ->{//SI EL ESTADO OBJETIVO ESTA HACIENDO LA TRANSICIÓN A EL ESTADO BoxColorState.PRIMARY USAMOS UNA TRANSICIÓN U OTRA>
                    spring(dampingRatio = Spring.DampingRatioHighBouncy, stiffness = 50f)
                }
                else -> {
                    tween(durationMillis = 1000)
                }
            }
        },
        label = ""
    ) { boxColorState: BoxColorState ->//ACÁ TENEMOS ACCESO AL ESTADO CON EL QUE ESTÁ TRABAJANDO EL OBJETO Transition.
        when (boxColorState) {
            BoxColorState.PRIMARY -> Color.Green
            BoxColorState.BACKGROUND -> Color.Magenta
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = colorAnimado)//ACÁ USAMOS EL COLOR QUE TIENE ANIMACIÓN.
            .clickable {
                state = when (state) {//Se cambia el estado del cuadro
                    BoxColorState.PRIMARY -> BoxColorState.BACKGROUND
                    BoxColorState.BACKGROUND -> BoxColorState.PRIMARY
                }
            }
    )

}


@Preview(
    showSystemUi = true
)
@Composable
fun MisEjemplosDeAnimacionesBasicasDeColorConUpdateTransition(){
    TestAnimationTheme {
        Surface {
            MiAnimacionBasicaDeColorUsandoUpdateTransition()
        }
    }
}