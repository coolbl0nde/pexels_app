package com.example.feature.presentation.details.presentation.component

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.zIndex
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

    var offsetX by remember {
        mutableStateOf(0f)
    }
    var offsetY by remember {
        mutableStateOf(0f)
    }

    val minScale = 1f
    val maxScale = 4f

    val stateModifier = Modifier
        .graphicsLayer(
            scaleX = scale,
            scaleY = scale,
            translationX = offsetX,
            translationY = offsetY,
        )
        .pointerInput(Unit) {
            detectTransformGestures { _, pan, zoom, _ ->

                val newScale = scale * zoom
                scale = newScale.coerceIn(minScale, maxScale)

                val centerX = size.width / 2
                val centerY = size.height / 2

                val offsetXChange = (centerX - offsetX) * (newScale / scale - 1)
                val offsetYChange = (centerY - offsetY) * (newScale / scale - 1)

                val maxOffsetX = (size.width / 2) * (scale - 1)
                val minOffsetX = -maxOffsetX
                val maxOffsetY = (size.height / 2) * (scale - 1)
                val minOffsetY = -maxOffsetY

                if (scale * zoom <= maxScale) {
                    offsetX = (offsetX + pan.x * scale + offsetXChange)
                        .coerceIn(minOffsetX, maxOffsetX)
                    offsetY = (offsetY + pan.y * scale + offsetYChange)
                        .coerceIn(minOffsetY, maxOffsetY)
                }
            }
        }
        .pointerInput(Unit){
            detectTapGestures(
                onDoubleTap = {
                    if (scale != 1f){
                        scale = 1f
                        offsetX = 0f
                        offsetY = 0f
                    } else {
                        scale = 2f
                    }
                }
            )
        }


    SubcomposeAsyncImage(
        modifier = stateModifier
            .padding(horizontal = if (scale == 1f) 20.dp else 0.dp)
            .aspectRatio(width.toFloat() / height)
            .clip(if (scale == 1f) RoundedCornerShape(10) else RoundedCornerShape(0))
            .zIndex(1f)
            .verticalScroll(rememberScrollState()),
        model = imageUrl,
        contentDescription = null,
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