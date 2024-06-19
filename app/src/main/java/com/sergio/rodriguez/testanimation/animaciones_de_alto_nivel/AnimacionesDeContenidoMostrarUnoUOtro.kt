package com.sergio.rodriguez.testanimation.animaciones_de_alto_nivel

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.EnterExitState
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.sergio.rodriguez.testanimation.ui.theme.TestAnimationTheme

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MiAnimacionDeContenidoBasica(
    name: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        var count: Int by remember { mutableIntStateOf(value = 0) }//ESTADO CUALQUIERA QUÉ SIRVE DISPARAR LA ANIMACIÓN CON SU CAMBIO.

        Button(onClick = { count++ } ) {//ACÁ SE CAMBIA EL VALOR DEL ESTADO, POR ENDE SE DISPARA LA ANIMACIÓN.
            Text(text = "add")
        }

        AnimatedContent(
            targetState = count,//ESTE ES EL ESTADO/VALOR QUE VAMOS A ESTAR ESCUCHANDO, SI ESTE CAMBIA SE EJECUTA LA ANIAMCIÓN Y ASI SUCESIVAMENTE
            label = "Animación de contenido simple"//ES UTIL PARA DEPURACIÓN, PERFILADO, ETC.
        ) { targetStateCount: Int ->//ESTADO ACTUAL DE LA ANIMACIÓN
            val backGroundColor by this.transition.animateColor(label = "Animación Interna") { stateMainAnimation: EnterExitState ->//ESTADO DE LA ANIMACIÓN PRINCIPAL OSEA DE AnimatedContent
                if(stateMainAnimation == EnterExitState.Visible){
                    Color.Red.copy(alpha = 0.3F)
                }else{
                    Color.Green.copy(alpha = 0.9F)
                }
            }
            when(targetStateCount){//DEPENDIENDO DEL ESTADO MOSTRAMOS UN CONTENIDO U OTRO
                0   -> Text(text = "No ha ocurrido un cambio", modifier = Modifier.background(color = backGroundColor))
                in 1..100 -> Text(text = "Count: $targetStateCount", modifier = Modifier.background(color = backGroundColor))
            }
        }
    }
}


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MiAnimacionDeContenidoPersonalizadaConTransicion(
    name: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        var count: Int by remember { mutableIntStateOf(value = 0) }//ESTADO CUALQUIERA QUÉ SIRVE DISPARAR LA ANIMACIÓN CON SU CAMBIO.

        Button(onClick = { count++ }) {//ACÁ SE CAMBIA EL VALOR DEL ESTADO, POR ENDE SE DISPARA LA ANIMACIÓN.
            Text(text = "add")
        }

        Button(onClick = { count-- }) {//ACÁ SE CAMBIA EL VALOR DEL ESTADO, POR ENDE SE DISPARA LA ANIMACIÓN.
            Text(text = "Minus")
        }

        AnimatedContent(
            targetState = count,//ESTE ES EL ESTADO/VALOR QUE VAMOS A ESTAR ESCUCHANDO, SI ESTE CAMBIA SE EJECUTA LA ANIAMCIÓN Y ASI SUCESIVAMENTE
            label = "Animación de contenido simple",//ES UTIL PARA DEPURACIÓN, PERFILADO, ETC.
            transitionSpec = {//ESTE LAMBDA ESPERA COMO RETORNO UN OBJETO ContentTransform QUE SE CREA A USANDO UN EFECTO DE ENTRADA Y UNO SE SALIDA
                //EN ESTE ÁMBITO PODEMOS OBTENER EL VALOR INICIAL Y EL VALOR OBJETIVO O FINAL PARA HACER COMPARACIONES POR EJEMPLO.
                if (this.targetState > this.initialState) {//COMPARAMOS EL NUEVO ESTADO CON EL ANTERIOR, LA PRIMERA VEZ COMO NO HAY ESTADO ANTERIOR NO SE HACE NADA, PORQUE AMBOS VALORES SON IGUALES, ENTONCES NO HAY ANIMACIÓN.
                    //ACÁ UNIMOS UN EFECTO DE ENTRADA Y UNO DE SALIDA PARA MEDIANTE togetherWith CREAR EL OBJETO ContentTransform QUE REQUIERE  LA LAMBDA PRINCIPAL.
                    (slideInVertically { height -> height }.plus(enter = fadeIn()) ).togetherWith(exit = slideOutVertically { height -> -height } + fadeOut() )
                } else {
                    //USAMOS LOS EFECTOS CON LA SOBRECARGA DE LAMBDA POR QUE NO ESTAMOS USANDO TRANSICIONES FISICAS COMPLEJAS
                    slideInVertically { height -> -height } + fadeIn() togetherWith slideOutVertically { height -> height } + fadeOut()
                }.using(//CON EL OBJETO ContentTransform DEVUELTO POR EL IF USAMOS SOBRE EL LA FUNCIÓN INFIX USING QUE NOS PERMITE CONFIGURAR ASPECTOS DEL CONTENEDOR DE LA ANIMACIÓN.
                    sizeTransform = SizeTransform(clip = false)//ACÁ INDICAMOS QUE EL CONTENEDOR DE LA ANIMACIÓN SE AJUSTE AL TAMAÑO DEL CONTENIDO DE LA ANIMACIÓN, SIN LLEGAR A RECORTAR EL CONTENIDO.
                )
            },
            contentAlignment = Alignment.Center//ACÁ INDICAMOS COMO SE DEBE ALINEAR EL CONTENIDO DENTRO DEL ENVOLTORIO DE LA ANIMACIÓN.
        ) { targetStateCount ->

            val backGroundColor: Color by this.transition.animateColor(label = "Animación Interna") { stateMainAnimation: EnterExitState ->//ESTADO DE LA ANIMACIÓN PRINCIPAL OSEA DE AnimatedContent
                if(stateMainAnimation == EnterExitState.Visible){
                    Color.Red.copy(alpha = 0.3F)
                }else{
                    Color.Green.copy(alpha = 0.9F)
                }
            }

            when(targetStateCount){//DEPENDIENDO DEL ESTADO MOSTRAMOS UN CONTENIDO U OTRO
                0   -> Text(text = "El contador esta en 0", modifier = Modifier.background(color = backGroundColor))
                in 1..100 -> Text(text = "Count: $targetStateCount", modifier = Modifier.background(color = backGroundColor))
            }
        }
    }
}



@Composable
fun MiAnimacionDeContenidoPersonalizadoConContenidoDeDiferenteTamanio(
    name: String,
    modifier: Modifier = Modifier
) {
    var expanded: Boolean by remember { mutableStateOf(value = false) }//ESTADO QUE INDICA SI  QUÉ SIRVE DISPARAR LA ANIMACIÓN CON SU CAMBIO.

    Surface(
        color = MaterialTheme.colorScheme.onSecondary,
        onClick = { expanded = !expanded },//ACÁ SE CAMBIA EL VALOR DEL ESTADO, POR ENDE SE DISPARA LA ANIMACIÓN.
    ) {
        AnimatedContent(
            targetState = expanded,//ESTE ES EL ESTADO/VALOR QUE VAMOS A ESTAR ESCUCHANDO, SI ESTE CAMBIA SE EJECUTA LA ANIMACIÓN Y ASI SUCESIVAMENTE.
            label = "Animación de contenido simple",//ES UTIL PARA DEPURACIÓN, PERFILADO, ETC.
            transitionSpec = {//EN ESTA LAMBDA TENEMOS ALCANCE PARA CONOCER EL VALOR OBJETIVO DEL ESTADO

                fadeIn(//EFECTO DE ANIMACIÓN CON TRANSICIÓN COMPLEJA
                    animationSpec = tween(
                        durationMillis = 150,//DURACIÓN DE LA ANIMACIÓN
                        delayMillis = 150//TIEMPO QUE SE ESPERA PARA EJECUTAR LA TRANSICION
                    )
                ) togetherWith fadeOut(
                    animationSpec = tween(
                        durationMillis = 150,
                        delayMillis = 0
                    )
                ) using SizeTransform (//ESTE MÉTODO PERMITE INDICAR COMO TRANSFORMAR EL TAMAÑO DEL CONTENIDO DURANTE LA ANIMACIÓN.
                    sizeAnimationSpec = {
                        initialSize: IntSize,//TAMAÑO INICIAL DEL CONTENIDO CUANDO COMIENZA LA ANIMACIÓN.
                        targetSize: IntSize//TAMAÑO OBJETIVO DEL CONTENIDO AL FINALIZAR LA ANIMACIÓN.
                        ->
                        if ( this.targetState ) {
                            //EN ESTE CASO SI ES VERDADERO SABEMOS QUE VAMOS A MOSTRAR EL CONTENIDO EXPANDIDO
                            keyframes {//ESTA LAMBDA ES UNA FUNCIÓN DE EXTENSIÓN AL OBJETO KeyframesSpecConfig POR ENDE CON THIS PODEMOS ACCEDER A SUS PROPIEDADES.
                                //ACÁ INDICAMOS EL TAMAÑO OBJETIVO Y EL MOMENTO EN LA DURACCIÓN DE LA ANIMACIÓN EN EL CUAL SE LLEGARÁ A ESTE TAMAÑO
                                IntSize(width = targetSize.width, height = initialSize.height) at 350
                                this.durationMillis = 500//DURACIÓN DE LA ANIMACIÓN
                            }
                        } else {
                            keyframes {
                                //ACÁ INDICAMOS EL TAMAÑO OBJETIVO Y EL MOMENTO EN LA DURACCIÓN DE LA ANIMACIÓN EN EL CUAL SE LLEGARÁ A ESTE TAMAÑO
                                IntSize(width = initialSize.width, height = targetSize.height) at 350
                                durationMillis = 500//DURACIÓN DE LA ANIMACIÓN
                            }
                        }
                    }
                )
            }
        ) { targetStateExpanded: Boolean -> //ESTADO EN EL QUE SE ENCUENTRA LA ANIMACIÓN
            if (targetStateExpanded) {//SI EL ESTADO SE ENCUENTRA EN VERDADERO O FALSO MOSTRAMOS UNO U OTRO CONTENIDO
                Expanded()
            } else {
                ContentIcon()
            }
        }
    }
}

@Composable
fun Expanded(
    modifier: Modifier = Modifier,
    mutableInteractionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height = 200.dp)
            .background(color = Color.Blue) // Cambia el color de fondo según tus necesidades
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
fun ContentIcon(
    modifier: Modifier = Modifier,
    mutableInteractionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    Box(modifier = modifier.height(48.dp)){
        Icon(
            imageVector = Icons.Default.Call, // Cambia el ícono según tus necesidades
            contentDescription = "Icono de Contenido",
            tint = Color.Black, // Cambia el color del ícono según tus necesidades
            modifier = Modifier
                .size(48.dp) // Cambia el tamaño del ícono según tus necesidades
        )
    }
}



@Preview(
    showSystemUi = true
)
@Composable
fun MisEjemplosDeAnimacionesDeContenido(){
    TestAnimationTheme {
        Surface {
            MiAnimacionDeContenidoPersonalizadaConTransicion(name = "Pulsame")
        }
    }
}