package com.example.core.utils

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput

val String.Companion.empty: String
    get() = ""

//enum class State { Pressed, Idle }
//
//@OptIn(ExperimentalFoundationApi::class)
//fun Modifier.bouncingClickable(
//    onClick: () -> Unit,
//) = composed {
//    var state by remember { mutableStateOf(State.Idle) }
//    val scale by animateFloatAsState(if (state == State.Pressed) 0.70f else 1f)
//
//    this
//        .graphicsLayer {
//            scaleX = scale
//            scaleY = scale
//        }
//        .clickable (
//            interactionSource = remember { MutableInteractionSource() },
//            indication = null,
//            onClick = onClick
//        )
//        .pointerInput(state) {
//            awaitPointerEventScope {
//                state = if (state == State.Pressed) {
//                    waitForUpOrCancellation()
//                    State.Idle
//                } else {
//                    awaitFirstDown(false)
//                    State.Pressed
//                }
//            }
//        }
//}