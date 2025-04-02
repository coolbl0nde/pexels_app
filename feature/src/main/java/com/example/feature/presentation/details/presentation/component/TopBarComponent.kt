package com.example.feature.presentation.details.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.feature.R

@Composable
fun TopBarComponent(
    photographer: String,
    onBackClick: () -> Unit,
) {

    Box (
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        contentAlignment = Alignment.CenterStart,
    ) {
        IconButton(
            modifier = Modifier
                .clip(RoundedCornerShape(40))
                .background(MaterialTheme.colorScheme.surface),
            onClick = onBackClick,
        ) {
            Icon(
                painter = painterResource(R.drawable.back_icon),
                contentDescription = stringResource(R.string.back),
                tint = MaterialTheme.colorScheme.onSurface
            )
        }

        Text(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 45.dp),
            text = photographer,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight(700),
            fontSize = 18.sp,
        )
    }
}