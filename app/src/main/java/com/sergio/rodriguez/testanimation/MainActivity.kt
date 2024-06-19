package com.sergio.rodriguez.testanimation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.sergio.rodriguez.testanimation.animaciones_de_alto_nivel.animaciones_sin_composables_de_animacion.MiAnimacionInfinitaDeColorInfiniteColor
import com.sergio.rodriguez.testanimation.animaciones_de_bajo_nivel.ExampleAnimatableBasic
import com.sergio.rodriguez.testanimation.ui.theme.TestAnimationTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestAnimationTheme {

                if(BuildConfig.BUILD_TYPE == "debug") {
                    MiAnimacionInfinitaDeColorInfiniteColor()
                }

                if(BuildConfig.FLAVOR  == "free"){
                    MiAnimacionInfinitaDeColorInfiniteColor()
                }else{
                    ExampleAnimatableBasic()
                }

                val url = BuildConfig.SHOW_POPUP

                Log.d("MainActivity", "MOSTRAR POPUP: $url")

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
        /*Animaciones de Bajo nivel*/
       //ExampleAnimatableBasic()
    }
}