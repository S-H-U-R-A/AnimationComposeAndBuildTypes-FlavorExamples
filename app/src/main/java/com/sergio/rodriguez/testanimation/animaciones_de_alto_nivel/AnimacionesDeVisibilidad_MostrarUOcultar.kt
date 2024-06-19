package com.sergio.rodriguez.testanimation.animaciones_de_alto_nivel

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterExitState
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.sergio.rodriguez.testanimation.ui.theme.TestAnimationTheme

//ESTAS ANIMACIONES SE USAN BÁSICAMENTE PARA CUANDO TENEMOS ALGO QUE
//QUEREMOS MOSTRAR U OCULTAR PERO CON UN EFECTO AGRADABLE PARA EL USUARIO

@Composable
fun MiAnimacionDeVisibilidadBasica(
    name: String,
    modifier: Modifier = Modifier
) {
    //ESTADO PARA SABER SI SE MUESTRA LA ANIMACIÓN QUE CONTIENE EL TEXTO, POR DEFECTO SE ESTA OCULTO
    var visible: Boolean by remember { mutableStateOf(false) }

    val configuration = LocalConfiguration.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxSize()
            .border(width = 1.dp, color = Color.Red)
    ) {
        //ESTE COMPOSABLE ES UN ENVOLTORIO QUÉ PERMITE APLICAR UNA ANIMACIÓN A SU CONTENIDO
        AnimatedVisibility(
            visible = visible,
            enter = expandVertically(
                animationSpec = spring(stiffness = Spring.StiffnessMediumLow, visibilityThreshold = IntSize.VisibilityThreshold),
                expandFrom = Alignment.Top,
                clip = true,
                initialHeight = {
                    50
                }
            ),
            exit = shrinkVertically(
                animationSpec = spring(stiffness = Spring.StiffnessMediumLow, visibilityThreshold = IntSize.VisibilityThreshold),
                shrinkTowards = Alignment.Bottom,
                clip = true,
                targetHeight = {
                    0
                }
            )
        ) {
            //EN ESTA LAMBDA VA EL CONTENIDO QUE QUEREMOS QUE AL MOSTRARSE U OCULTARSE USE UNA ANIMACIÓN
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(width = 1.dp, color = Color.Green)
            ) {
                Text(//COMPOSABLE A SER ANIMADO
                    text = "Hola usuario",
                    modifier = Modifier
                )
                Text(//COMPOSABLE A SER ANIMADO
                    text = "Hola Soy Otro Texto ",
                    modifier = Modifier
                )
            }
        }

        Spacer(modifier = Modifier.height(height = 32.dp))

        Button(
            onClick = {//CUANDO SE PULSE SOBRE EL BOTÓN SE CAMBIARÁ EL ESTADO DE VISIBLE O NO.
                visible = !visible
            },
            modifier = Modifier
        ) {
            Text(text = name)
        }
    }
}


@Composable
fun MiAnimacionDeVisibilidadPersonalizadaConEfectos(
    name: String, modifier: Modifier = Modifier
) {
    //ESTADO PARA SABER SI SE EJECUTA  LA ANIMACIÓN QUE CONTIENE EL TEXTO, POR DEFECTO SE ESTA OCULTO
    var visible: Boolean by remember { mutableStateOf(value = false) }

    //ACÁ OBTENEMOS LA DENSIDAD DE PIXELES DE LA PANTALLA ACTUAL
    val density: Density = LocalDensity.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxSize()
    ) {
        //ESTE COMPOSABLE ES UN ENVOLTORIO QUÉ PERMITE APLICAR UNA ANIMACIÓN A SU CONTENIDO
        AnimatedVisibility(
            visible = visible,//ESTADO CON EL QUE SE VA ACTIVAR LA ANIMACIÓN CUANDO ESTE CAMBIE A TRUE
            enter = slideInHorizontally(//EFECTO PARA LA ANIMACIÓN , ESTE INDICA COMO APARECE O SE OCULTA EL COMPOSABLE, EN ESTE CASO SE DESLIZA DE IZQUIERDA A DERECHA HORIZONTALMENTE
                animationSpec = spring(),//TRANSICIÓN DE REBOTE PARA EL EFECTO DE LA ANIMACIÓN, LA TRANSICION LO QUE HACE ES INDICAR MÁS ASPECTOS COMPLEJOS PARA EL EFECTO EN ESTE CASO
                initialOffsetX = {//ESTA LAMBDA RETORNA EL ANCHO DEL CONTENIDO DE LA ANIMACIÓN
                    with(density) { 40.dp.roundToPx() }//ACÁ INDICAMOS QUE APAREZCA LA ANIMACIÓN A 40 DP EN EL EJE X
                }
            )
            +//ESTE SIMBOLO ES UNA SOBRE CARGA INFIX PARA EL MÉTODO PLUS() DE LOS EFECTOS DE ANIMACIÓN QUE PERMITE IR UNIENDO EFECTOS PARA LOGRAR UN EFECTO MÁS COMPLEJO
            expandHorizontally(//OTRO EFECTO DE ANIMACIÓN QUE INDICA QUE EL CONTENIDO SE EXPANDA HORIZONTALMENTE
                expandFrom = Alignment.Start//SE INDICA QUE SE EXPANDA DESDE LA IZQUIERDA OPOR ESO SE PONE START.
            ).plus(//ESTE ES EL MÉTODO NORMAL PARA UNIR VARIOS EFECTOS
                fadeIn(//OTRO EFECTO DE ANIMACIÓN QUE PONE UNA OPACIDAD INICIAL DEL 30%
                    initialAlpha = 0.5f
                )
            ),
            exit = slideOutHorizontally()//EFECTO PARA LA ANIMACIÓN, EN ESTE CASO LO USAMOS PARA LA SALIDA DEL CONTENIDO
                    .plus(
                        exit = shrinkHorizontally(//OTRO EFECTO DE ANIMACIÓN PARA REDUCIR HORIZONTALMENTE
                            shrinkTowards = Alignment.End//ACÁ INDICAMOS QUE SE REDUZCA DE DERECHA A IZQUIERDA
                        )
                    )
                    +
                    fadeOut()//OTRO EFECTO PARA LA ANIMACIÓN, QUE OCULTA EL CONTENIDO CON OPACIDAD
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
fun MiAnimacionDeVisibilidadPersonalizadaConElCicloDeVidaDeLaAnimacion(
    name: String,
    modifier: Modifier = Modifier
) {
    //ESTADO PARA LA ANIMACIÓN QUÉ NOS PERMITIRÁ OBTENER INFORMACIÓN DE LA ANIMACIÓN
    val state: MutableTransitionState<Boolean> = remember {
        MutableTransitionState(
            initialState = false//SE INDICA UN VALOR INICIAL PARA EL ESTADO.
        ).apply {
            //ACÁ INDICAMOS EL ESTADO OBJETIVO POR EL CÚAL SE ACTIVARÁ LA ANIMACIÓN.
            this.targetState = true
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //ANIMACIÓN PARA EL TEXTO
        AnimatedVisibility(
            visible = state.targetState,
        ) {
            Text(text = "TE AMO DARLYN", modifier = modifier.padding(top = 32.dp))//CONTENIDO QUE VA A SER ANIMADO.
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = when {
                //ESTA VARIABLE NOS RETORNA SI HAY ANIMACIONES EN PROGRESO
                state.isIdle//TRUE INDICA QUE NO HANIMACIONES EN PROGRESO; FALSE INDICA QUER HAY ANIMACIONES EN PROGRESO
                &&
                //ESTADO ACTUAL DE LA ANIMACIÓN(ESTA INICIA CON initialState Y CAMBIA CUANDO CAMBIAMOS O CONFIGURAMOS targetState)
                state.currentState
                -> "VISIBLE"
                !state.isIdle && state.currentState -> "DESAPARECIENDO"
                state.isIdle && !state.currentState -> "INVISIBLE"
                else -> "APARECIENDO"
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
fun MiAnimacionDeVisibilidadDeContenidoSecundario(
    name: String,
    modifier: Modifier = Modifier)
{
    //ESTADO PARA MOSTRAR O NO LA ANIMACIÓN
    var visible by remember { mutableStateOf(value = false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(
            visible = visible,//ESTADO PARA MOSTRAR U OCULTAR EL CONTENIDO MEDIANTE LA ANIMACIÓN.
            enter = EnterTransition.None,//ACÁ INDICAMOS QUE NO QUEREMOS HACER LA ANIMACION DE ENTRADA PRINCIPAL.
            exit = ExitTransition.None//ACÁ INDICAMOS QUE NO QUEREMOS HACER LA ANIMACION DE SALIDA PRINCIPAL.
        ) {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(Color.DarkGray)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .align(alignment = Alignment.Center)
                        .animateEnterExit(//ESTE MODIFICADOR SOLO SE PUEDE USAR EN EL AMBITO DE LA INTERFAZ AnimatedVisibilityScope
                            enter = slideInHorizontally() + fadeIn(),
                            exit = slideOutHorizontally().plus(exit = fadeOut())
                        )
                        .sizeIn(//DEFINIMOS EL TAMAÑO MINIMO QUE PUEDE TENER LA CAJA
                            minWidth = 256.dp,
                            minHeight = 64.dp
                        )
                        .background(color = Color.LightGray)
                ) {
                    //CONTENIDO
                    Button(
                        onClick = {
                            visible = !visible//CUANDO PULSEMOS SE OCULTARÁ EL ENVOLTORIO DE ANIMACIÓN PRINCIPAL
                        }
                    ) {
                        Text(text = "Hello Darlyn", modifier = modifier)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                visible = !visible//CAMBIAMOS EL ESTADO PARA QUE SE MUESTRE LA ANIMACIÓN
            },
            modifier = Modifier
        ) {
            Text(text = name)
        }
    }


}


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MiAnimacionDeVisibilidadConAnimacionInternaConBaseEnElEstadoDeLaAnimacionPrincipal(
    name: String,
    modifier: Modifier = Modifier
) {
    //ESTADO PARA LA ANIMACIÓN PRINCIPAL
    var visible by remember { mutableStateOf(value = false) }

    Column {
        AnimatedVisibility(
            visible = visible,//ESTADO DE VISIBILIDAD PARA LA ANIMACIÓN
            enter = fadeIn(),//DEFINIMOS EL EFECTO DE ENTRADA
            exit = fadeOut(),//DEFINIMOS EL EFECTO DE SALIDA
        ) {
            //USAMOS LA PROPIEDAD transition QUE ES UN OBJETO DE ÁMBITO DE AnimatedVisibilityScope QUE PERMITE DEFINIR
            //OTROS TIPOS DE ANIMACIONES QUE SE EJECUTARÁN CON BASE EN LOS ESTADOS DE CICLO DE VIDA DEVUELTOS POR LA ANIMACIÓN PRINCIPAL
            val background: Color by this.transition.animateColor(label = "") { state: EnterExitState ->//ESTADO DE CICLO DE VIDA DE LA ANIMACIÓN PRINCIPAL [PreEnter, Visible, PostExit]
                if (state == EnterExitState.PreEnter){//ACÁ VAMOS A CREAR UN COLOR DINAMICO CON BASE EN EL ESTADO DEL CICLO DE VIDA DE LA ANIMACIÓN PRINCIPAL.
                    Color.Blue
                } else {
                    Color.Green
                }
            }

            Box(
                modifier = Modifier
                    .size(size = 128.dp)
                    .background(background)//ESTE COLOR SERÁ DINAMICO CON BASE EN LOS ESTADOS DE CICLO DE VIDA DE LA ANIMACIÓN PRINCIPAL
            )

        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                visible = !visible//CAMBIAMOS EL ESTADO DE LA ANIMACIÓN PRINCIPAL
            },
            modifier = Modifier
        ) {
            Text(text = name)
        }
    }
}


@Preview(
    showSystemUi = true
)
@Composable
fun MisEjemplosDeAnimacionesDeAltoNivelDeVisibilidadPreview(){
    TestAnimationTheme {
        Surface(
            color = MaterialTheme.colorScheme.inversePrimary,
            modifier = Modifier.fillMaxSize()
        ) {
            MiAnimacionDeVisibilidadConAnimacionInternaConBaseEnElEstadoDeLaAnimacionPrincipal(name = "Pulsame")
        }
    }
}