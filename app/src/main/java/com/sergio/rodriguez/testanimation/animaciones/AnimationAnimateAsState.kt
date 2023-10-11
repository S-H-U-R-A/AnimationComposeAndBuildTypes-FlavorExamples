package com.sergio.rodriguez.testanimation.animaciones

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp



@Composable
fun AnimateAsStateFloat(
    name: String,
    modifier: Modifier = Modifier
) {

    var enabled by remember {
        mutableStateOf(false)
    }

    val alpha: Float by animateFloatAsState(
        targetValue =  if (enabled) 1f else 0.2f,
        label = ""
    )

    Column {
        Box(
            Modifier
                .fillMaxWidth()
                .height(100.dp)
                .graphicsLayer(alpha = alpha)
                .background(Color.Red)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { enabled = !enabled },
            modifier = Modifier
        ) {
            Text(text = name)
        }
    }
}

@Composable
fun AnimateAsStateColor(
    name: String,
    modifier: Modifier = Modifier
) {

    var enabled by remember {
        mutableStateOf(false)
    }

    val color by animateColorAsState(
        targetValue = if (enabled) MaterialTheme.colorScheme.tertiaryContainer else MaterialTheme.colorScheme.primaryContainer,
        label = ""
    )

    Column {
        Box(
            Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(color)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { enabled = !enabled },
            modifier = Modifier
        ) {
            Text(text = name)
        }
    }
}

@Composable
fun AnimateAsStateColorCustom(
    name: String,
    modifier: Modifier = Modifier
) {

    var enabled by remember {
        mutableStateOf(false)
    }

    val color by animateColorAsState(
        targetValue = if (enabled) MaterialTheme.colorScheme.tertiaryContainer else MaterialTheme.colorScheme.primaryContainer,
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing),
        label = ""
    )

    Column {
        Box(
            Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(color)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { enabled = !enabled },
            modifier = Modifier
        ) {
            Text(text = name)
        }
    }
}