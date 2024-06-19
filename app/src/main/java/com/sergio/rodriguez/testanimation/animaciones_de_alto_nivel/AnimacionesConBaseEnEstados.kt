package com.sergio.rodriguez.testanimation.animaciones_de_alto_nivel

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateSizeAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sergio.rodriguez.testanimation.ui.theme.TestAnimationTheme

//LAS DIFERENTES ANIMACIONES DE ESTADO SON LAS MÁS BÁSICAS EN COMPOSE Y
//ESTAS ANIMACIONES TRABAJAN EN FUNCIÓN DE ALGUN ESTADO QUE TENGAMOS PERMITIENDONOS
//ANIMAR PROPIEDADES O MODIFICADORES QUE USEN TIPOS DE DATOS COMO [Dp, Color, Value, Size, Offset, Rect, Int, IntOffset, IntSize]

@Composable
fun MiAnimacionDeEstadoParaFloat(
    name: String,
    modifier: Modifier = Modifier
) {
    var enabled by remember { mutableStateOf(value = false) }//ESTADO CUALQUIERA CON EL QUE TRABAJARÁ LA ANIMACIÓN

    val context: Context = LocalContext.current//CONTEXTO PARA EL MENSAJE DE FINALIZACIÓN

    val alpha: Float by animateFloatAsState(
        //VALOR FINAL AL QUE SE QUIERE LLEGAR EN LA ANIMACIÓN
        //SI ESTE VALOR CAMBIA DINAMICAMENTE SE DISPARA LA ANIMACIÓN
        targetValue = if (enabled){//SI EL ESTADO ES VERDADERO EN ESTE CASO EL VALOR OBJETIVO SERÁ UNA OPACIDAD DE 100%, DE LO CONTRARIO LA OPCACIDAD SERÁ DEL 20%
            1f
        } else{
            0.2f
        },
        animationSpec = keyframes(
            init = {
                this.durationMillis = 5000//DURACCIÓN DE LA TRANSICCION
                this.delayMillis = 500//RETRASO PARA EMPEZAR LA ANIMACIÓN
                0f at 0 using LinearOutSlowInEasing//ACÁ INDICAMOS QUE AL MILISEGUNDO 0, EL TARGET VALUE SERÁ DE 0F Y USARÁ UNA ACELERACIÓN.
                0.2f at 1000 using FastOutSlowInEasing//ACÁ INDICAMOS QUE AL MILISEGUNDO 1000, EL TARGET VALUE SERÁ DE 0.2F Y USARÁ UNA ACELERACIÓN.
                0.5f.at(timeStamp = 3000)//ACÁ INDICAMOS QUE AL MILISEGUNDO 3000, EL TARGET VALUE SERÁ DE 0.5F Y USARÁ UNA ACELERACIÓN.
                0.8f at 4500
            }
        ),
        visibilityThreshold = 0.4f,//UNA POSICIÓN EN EL RANGO DE LA ANIMACIÓN EN LA QUE SE CONSIDERA XCERCANO AL TARGET-VALUE
        label = "Animación de Flotante"
    ){
        //LISTENER PARA SABER CUANDO FINALIZA ESTE TIPO DE ANIMACIÓN
        Toast.makeText(context, "La Animación Finalizo", Toast.LENGTH_SHORT).show()
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(height = 180.dp)
                .graphicsLayer(alpha = alpha)//APLICAMOS UNA TRANSFORMACIÓN DE OPACIDAD A ESTE COMPASABLE CON BASE EN EL ALPHA DINÁMICO QUE HEMOS CREADO COMO ANIMACIÓN.
                .background(color = Color.Red)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { enabled = !enabled },//EVENTO QUE CAMBIA EL VALOR DEL ESTADO USADO POR LA ANIMACIÓN
            modifier = Modifier
        ) {
            Text(text = name)
        }
    }
}

@Composable
fun MiAnimacionDeEstadoParaColor(
    name: String,
    modifier: Modifier = Modifier
) {
    var enabled by remember { mutableStateOf(false) }//ESTADO CUALQUIERA CON EL QUE TRABAJARÁ LA ANIMACIÓN.

    val context: Context = LocalContext.current//CONTEXTO PARA EL MENSAJE DE FINALIZACIÓN

    val color: Color by animateColorAsState(
        //VALOR FINAL AL QUE SE QUIERE LLEGAR EN LA ANIMACIÓN.
        //SI ESTE VALOR LO CAMBIAMOS DINÁMICAMENTE SE DISPARA LA ANIMACIÓN NUEVAMENTE PORQUE EL VALOR FINAL CAMBIARÍA Y SE DEBE REALIZAR NUEVAMENTE LA ANIMACIÓN.
        targetValue = if (enabled){
            MaterialTheme.colorScheme.tertiaryContainer
        } else {
            MaterialTheme.colorScheme.inversePrimary
        },
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing),//TRANSICCIÓN DE LA FISICA DE LA ANIMACIÓN
        label = "Animnación de Color"//ES UTIL PARA DEPURACIÓN, PERFILADO, ETC
    ){
        //LISTENER PARA SABER CUANDO FINALIZA ESTE TIPO DE ANIMACIÓN
        Toast.makeText(context, "La Animación de Color ha Finalizo", Toast.LENGTH_SHORT).show()
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(color = color)//APLICAMOS UN COLOR DE FONDO A ESTE COMPASABLE CON BASE EN EL COLOR DINÁMICO QUE HEMOS CREADO COMO ANIMACIÓN.
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { enabled = !enabled },//EVENTO QUE CAMBIA EL VALOR DEL ESTADO USADO POR LA ANIMACIÓN
            modifier = Modifier
        ) {
            Text(text = name)
        }
    }
}

@Composable
fun MiAnimacionDeEstadoParaTamanioSize(
    name: String,
    modifier: Modifier = Modifier
) {
    var enabled by remember { mutableStateOf(false) }//ESTADO CUALQUIERA CON EL QUE TRABAJARÁ LA ANIMACIÓN.

    val coordenadasTamanio: Size by animateSizeAsState(
        //VALOR FINAL AL QUE SE QUIERE LLEGAR EN LA ANIMACIÓN.
        //SI ESTE VALOR LO CAMBIAMOS DINÁMICAMENTE SE DISPARA LA ANIMACIÓN NUEVAMENTE PORQUE EL VALOR FINAL CAMBIARÍA Y SE DEBE REALIZAR NUEVAMENTE LA ANIMACIÓN.
        targetValue = if(enabled){
            Size(width = 3F, height = 3F)
        }else {
            Size(width = 1F, height = 1F)
        },
        animationSpec = repeatable(
            iterations = 10,
            animation = tween(durationMillis = 2000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "Animación de Tamaño con OffSet"//ES UTIL PARA DEPURACIÓN, PERFILADO, ETC
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {

        Box(
            modifier = Modifier
                .graphicsLayer(//APLICAMOS UNA TRANSFORMACIÓN DE ESCALADO A ESTE COMPASABLE CON BASE EN EL TAMAÑO DINÁMICO QUE HEMOS CREADO COMO ANIMACIÓN.
                    scaleX = coordenadasTamanio.width,//VALOR ORIGINAL POR EL NÚMERO QUE VALGA width
                    scaleY = coordenadasTamanio.height//VALOR ORIGINAL POR EL NÚMERO QUE VALGA height
                )
                .background(color = Color.Red)
        ){
            Text(
                text = "Caja de Prueba",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { enabled = !enabled },//EVENTO QUE CAMBIA EL VALOR DEL ESTADO USADO POR LA ANIMACIÓN
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
fun MisEjemplosDeAnimacionesComoEstadosPreview(){
    TestAnimationTheme {
        Surface(
            color = MaterialTheme.colorScheme.inversePrimary,
            modifier = Modifier.fillMaxSize()
        ) {
            MiAnimacionDeEstadoParaTamanioSize(name = "Pulsame")
        }
    }
}