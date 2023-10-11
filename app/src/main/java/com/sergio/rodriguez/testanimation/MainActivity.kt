package com.sergio.rodriguez.testanimation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.sergio.rodriguez.testanimation.animaciones.AnimatedCrossfade
import com.sergio.rodriguez.testanimation.ui.theme.TestAnimationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestAnimationTheme {
                AnimatedCrossfade("Click me")
            }
        }
    }
}


@Preview(
    showSystemUi = true,
)
@Composable
fun AnimatedVisibilityPreview() {
    TestAnimationTheme {

        /*Animaciones de visibilidad */
        //AnimatedVisibilityBasic("Click me")
        //AnimatedVisibilityCustom("Click me")
        //AnimatedVisibilityWitLifeCycle("Click me")
        //AnimatedVisibilitySecondaryElements("Click me")
        //AnimatedVisibilitySecondaryElementsCustom("Click me")

        /*Animaciones a partir de estados o valores*/
        //AnimateAsStateFloat("Click me")
        //AnimateAsStateColor("Click me")
        //AnimateAsStateColorCustom("Click me")

        /*Animaciones del contenido de un composable usando
          el valor del estado en el mismo composable
        */
        //AnimateContentBasic("Click me")
        //AnimateContentCustom("Click me")
        //AnimateContentCustomAdvance("Click me")

        /*Animaciones para el cambio de tamaño de un composable*/
        //AnimatedContentSize("Click me")

        /*Animaciones para*/
        AnimatedCrossfade("Click me")

    }
}