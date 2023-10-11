package com.sergio.rodriguez.testanimation.animaciones

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

enum class Page {
    A, B
}

@Composable
fun AnimatedCrossfade(name: String, modifier: Modifier = Modifier) {
    var currentPage: Page by remember { //Estado inicial, en este caso para las páginas.
        mutableStateOf(Page.A)
    }

    Column(modifier = modifier) {

        Button(onClick = { currentPage = Page.A }) {//Evento que cambia la página, por ende ejecuta la animación.
            Text("Page A")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { currentPage = Page.B }) {//Evento que cambia la página, por ende ejecuta la animación.
            Text("Page B")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Crossfade(targetState = currentPage, label = "") { screen -> //permite usar el valor del estado para cambiar el composablea mostrar.
            when (screen) {
                Page.A -> Text("Page A")
                Page.B -> Text("Page B")
            }
        }

    }
}

