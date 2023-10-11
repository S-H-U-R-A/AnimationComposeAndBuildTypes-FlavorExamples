package com.sergio.rodriguez.testanimation.animaciones

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

@Composable
fun AnimatedContentSize(name: String, modifier: Modifier = Modifier) {

    var message by remember {
        mutableStateOf("Hello")
    }

    Column {
        Box(
            modifier = Modifier
                .background(Color.Blue)
                .animateContentSize()
        ) {
            Text(text = message)//Cómo se modifica el texto y se cambia el tamaño de este composable, .animateContentSize() no genera una animación para esto.
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { message = "Lorem ipsum dolor sit amet, consectetur adipiscing elit." } ) {
            Text(text = "Click me")
        }
    }

}