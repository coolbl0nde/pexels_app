package com.example.feature.presentation.details.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.feature.R

@Composable
fun BottomBarComponent(
    onDownloadImage: () -> Unit,
    onUpdateFavoriteStatus: () -> Unit,
    isFavorite: Boolean,
) {

    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        verticalAlignment = Alignment.Bottom,
    ) {
        Row (
            modifier = Modifier
                .clip(RoundedCornerShape(50))
                .background(MaterialTheme.colorScheme.surface),
            verticalAlignment = Alignment.CenterVertically,
        ) {

            IconButton(
                onClick = onDownloadImage,
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(MaterialTheme.colorScheme.primary)
            ) {
                Icon(
                    painter = painterResource(R.drawable.download_icon),
                    contentDescription = stringResource(R.string.download),
                    tint = MaterialTheme.colorScheme.onPrimary,
                )
            }

            Text(
                modifier = Modifier.padding(horizontal = 20.dp),
                text = stringResource(R.string.download),
                textAlign = TextAlign.Center,
            )
        }

        Spacer(Modifier.weight(1f))

        IconButton(
            modifier = Modifier
                .clip(RoundedCornerShape(50))
                .background(MaterialTheme.colorScheme.surface),
            onClick = onUpdateFavoriteStatus,
        ) {
            Icon(
                painter = if(isFavorite) painterResource(R.drawable.filled_favorite_icon)
                    else painterResource(R.drawable.favorite_icon),
                contentDescription = stringResource(R.string.download),
                tint = Color.Unspecified,
            )
        }
    }
}