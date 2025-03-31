package com.example.feature.presentation.details.presentation.component

import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage

@Composable
fun ImageZoomComponent(
    imageUrl: String,
    width: Int,
    height: Int,
) {

    var scale by remember {
        mutableStateOf(1f)
    }

    var offset by remember {
        mutableStateOf(Offset.Zero)
    }

    val stateModifier = Modifier
        /*.graphicsLayer(
            scaleX = scale.coerceIn(1f, 5f),
            scaleY = scale.coerceIn(1f, 5f),
            translationX = offset.x,
            translationY = offset.y,
        )
        .pointerInput(Unit) {
            detectTransformGestures { _, pan, zoom, _ ->
                scale *= zoom
                offset += pan
            }
        }*/


    SubcomposeAsyncImage(
        modifier = stateModifier
            .padding(horizontal = 20.dp)
            .aspectRatio(width.toFloat() / height)
            .clip(RoundedCornerShape(10)),
        model = imageUrl,
        contentDescription = null,
        contentScale = ContentScale.Fit,
        loading = {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        },
        error = {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        },
        alignment = Alignment.Center,
    )

}