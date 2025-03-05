package com.example.feature.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HorizontalListItem(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {

    Box (
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(if (isSelected) Color(0xFFBB1020) else Color(0xFFF3F5F9))
            .toggleable(
                value = isSelected,
                onValueChange = { onClick() }
            )
    ) {
        Text(
            text = text,
            color = if (isSelected) Color.White else Color.Black,
            fontSize = 14.sp,
            modifier = Modifier.padding(top = 10.dp, bottom = 10.dp, start = 20.dp, end = 20.dp)
        )
    }

}