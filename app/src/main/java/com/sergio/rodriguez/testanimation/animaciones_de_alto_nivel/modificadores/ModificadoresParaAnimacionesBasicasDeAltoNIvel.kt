package com.sergio.rodriguez.testanimation.animaciones_de_alto_nivel.modificadores

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
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
import androidx.compose.ui.graphics.LinearGradientShader
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.sergio.rodriguez.testanimation.ui.theme.TestAnimationTheme

/**
 * Mi animacion de visibilidad usando modificador para elementos secundarios
 *
 * ESTE MODIFICADOR [animateEnterExit] SE USA EN ELEMENTOS SECUNDARIOS DEL COMPOSABLE DE ANIMACIÓN [AnimatedVisibility]
 * YA QUE ESTE MÉTODO REQUIERE EL ALCANCE QUE [AnimatedVisibility] OFRECE
 *
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MiAnimacionDeVisibilidadUsandoModificadorParaElementosSecundarios(
    name: String,
    modifier: Modifier = Modifier
) {
    //ESTADO PARA MOSTRAR O NO LA ANIMACIÓN
    var visible by remember { mutableStateOf(value = false) }

    Box(
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = {
                visible = !visible//CAMBIAMOS EL ESTADO PARA QUE SE MUESTRE LA ANIMACIÓN
            },
            modifier = Modifier.align(alignment = Alignment.TopCenter)
        ) {
            Text(text = name)
        }

        AnimatedVisibility(
            visible = visible,//ESTADO PARA MOSTRAR U OCULTAR EL CONTENIDO MEDIANTE LA ANIMACIÓN.
            enter = EnterTransition.None,//ACÁ INDICAMOS QUE NO QUEREMOS HACER LA ANIMACION DE ENTRADA PRINCIPAL.
            exit = ExitTransition.None//ACÁ INDICAMOS QUE NO QUEREMOS HACER LA ANIMACION DE SALIDA PRINCIPAL.
        ) {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(color = Color.DarkGray.copy(alpha = 0.7f))
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .align(alignment = Alignment.Center)
                        .animateEnterExit(//ESTE MODIFICADOR SOLO SE PUEDE USAR EN EL ÁMBITO DE AnimatedVisibilityScope
                            enter = slideInVertically() + fadeIn(),
                            exit = slideOutVertically().plus(exit = fadeOut())
                        )
                        .sizeIn(
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
    }
}


/**
 * Mi animacion de contenido con el uso de modificador
 *
 * EN ESTE EJEMPLO VAMOS A USAR EL MODIFICADOR [animateContentSize] QUE ES PARECIDO
 * A LAS ANIMACIONES DE CONTENIDO USANDO EL COMPOSABLE [AnimatedContent]
 *
 */
@Composable
fun MiAnimacionDeContenidoConElUsoDeModificador(
    name: String,
    modifier: Modifier = Modifier
) {
    var message: String by remember { mutableStateOf(value = "Hola Darlyn") }//ESTADO PARA EL TEXTO QUE PUEDE CAMBIAR.

    val contexto: Context = LocalContext.current//CONTEXTO PARA MOSTRAR UN MENSAJE

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(all = 32.dp)
    ) {
        Box(
            modifier = Modifier
                .background(color = Color.DarkGray)
                .animateContentSize(//ESTE MODIFICADOR DEBE IR ANTES DE CUALQUIER OTRO MODIFICADOR DE TAMAÑO, ESTO PARA QUE LOS OTROS SEPAN EL CAMBIO
                    animationSpec = spring(),
                    finishedListener = { initialSize: IntSize, targetSize: IntSize ->
                        Toast
                            .makeText(
                                contexto,
                                "Finalizo la animación de contenido simple",
                                Toast.LENGTH_SHORT
                            )
                            .show()
                    }
                )
        ) {
            Text(
                color= Color.White,
                text = message,//ESTE ES EL TEXTO QUE ESTAMOS ALMACENANDO EN EL ESTADO
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            //CUANDO PULSAMOS CAMBIAMOS EL TEXTO Y POR ENDE AUMENTA EL TAMAÑO DE LOS COMPOSABLES
            onClick = { message = "Darlyn te Amo muchismo esposita hermosa, quiero que te cases conmigo aceptas?" }
        ) {
            Text(text = "Click me")
        }
    }
}


@Preview(
    showSystemUi = true
)
@Composable
fun MisEjemploDeModificadorDeAnimacionesDeAltoNivelPreview(){
    TestAnimationTheme {
        Surface(
            color = MaterialTheme.colorScheme.inversePrimary,
            modifier = Modifier.fillMaxSize()
        ) {
            MiAnimacionDeContenidoConElUsoDeModificador(name = "Pulsame")
        }
    }
}