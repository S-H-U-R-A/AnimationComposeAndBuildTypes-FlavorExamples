package com.sergio.rodriguez.testanimation.animaciones_de_alto_nivel.animaciones_sin_composables_de_animacion

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Icon
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.sergio.rodriguez.testanimation.ui.theme.TestAnimationTheme

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MiAnimacionMultipleUsandoTransitionyUpdateTransition(
    modifier: Modifier = Modifier
){
    var selectedState: Boolean by remember { mutableStateOf(value = false) }//SE CREA Y SE RECUERDA EL ESTADO CON SU VALOR INICIAL.

    val transition = updateTransition(targetState = selectedState, label = "Animaciones Multiples")//ACÁ SE CREA UN OBJETO TRANSITION Y SE LE CONFIGURA UN ESTADO.

    val borderColor: Color by transition.animateColor(label = "Animación de Color para bordes") { isSelectedState: Boolean ->//ESTADO DEL OBJETO Transition.
        if (isSelectedState) Color.Cyan else Color.White
    }

    val elevation: Dp by transition.animateDp(label = "Animación de tamaño") { isSelectedState: Boolean ->//ESTADO DEL OBJETO Transition.
        if (isSelectedState) 16.dp else 2.dp
    }

    Surface(
        onClick = { selectedState = !selectedState },//EVENTO QUE DISPARÁ EL CAMBIO EN EL ESTADO
        shape = RoundedCornerShape(size = 16.dp),
        border = BorderStroke(2.dp, borderColor),//ACÁ USAMOS EL COLOR QUE TIENE ANIMACIÓN PARA EL BORDE.
        shadowElevation = elevation,//ACÁ USAMOS EL COLOR QUE TIENE ANIMACIÓN.
        modifier = modifier.padding(all = 16.dp).zIndex(zIndex = 2F)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
        ) {

            Text(text = "Te Amo Darlyn")

            transition.AnimatedVisibility(//ACÁ DEFINIMOS OTRA ANIMACIÓN DESDE EL OBJETO Transition.
                visible = { isSelectedState: Boolean ->//ESTADO DEL OBJETO Transition.
                    isSelectedState
                },
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                Text(text = "Quieres Casarte conmido?")
            }

            //ACÁ DEFINIMOS OTRA ANIMACIÓN DESDE EL OBJETO Transition, EN ESTE CASO UNA ANIMACIÓN DE CONTENIDO.
            transition.AnimatedContent { isSelectedState: Boolean ->//ESTADO DEL OBJETO Transition.
                if (isSelectedState) {
                    Text(text = "Solo Acepto un si")
                } else {
                    Icon(imageVector = Icons.Default.FavoriteBorder, contentDescription = "Phone")
                }
            }
        }
    }
}

@Preview(
    showSystemUi = true
)
@Composable
fun MisEjemplosDeAnimacionesMultiplesConTransitionYUpdateTransitionPreview(){
    TestAnimationTheme {
        Surface {
            MiAnimacionMultipleUsandoTransitionyUpdateTransition()
        }
    }
}