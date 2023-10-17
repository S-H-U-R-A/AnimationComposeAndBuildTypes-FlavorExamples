package com.sergio.rodriguez.testanimation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.sergio.rodriguez.testanimation.animaciones.AnimatedCrossfade
import com.sergio.rodriguez.testanimation.animaciones.AnimatedReuseAnimationWithObjectTransition
import com.sergio.rodriguez.testanimation.animaciones.AnimatedUpdateTransitionBasic
import com.sergio.rodriguez.testanimation.animaciones.AnimatedUpdateTransitionImmediately
import com.sergio.rodriguez.testanimation.animaciones.AnimatedUpdateTransitionWithAnimatedVisibilityOrAnimatedContent
import com.sergio.rodriguez.testanimation.ui.theme.TestAnimationTheme

/**
 * Main activity
 *
 * @constructor Create empty Main activity
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestAnimationTheme {
                AnimatedReuseAnimationWithObjectTransition()
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

        /*Animaciones para intercambiar composables con una animación suave*/
        //AnimatedCrossfade("Click me")

        /*Animaciones para actualizar un composable con una animación suave, sin la necesidad de usar un composable de animación*/
        //AnimatedUpdateTransitionBasic()
        //AnimatedUpdateTransitionImmediately()
        //AnimatedUpdateTransitionWithAnimatedVisibilityOrAnimatedContent(name = "Visisble And Content")
        AnimatedReuseAnimationWithObjectTransition()
    }
}