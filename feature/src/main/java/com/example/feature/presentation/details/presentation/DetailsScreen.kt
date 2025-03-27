package com.example.feature.presentation.details.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.feature.R

@Composable
fun DetailsScreen() {

    Column ( modifier = Modifier.fillMaxSize()) {
        Box (
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            IconButton(
                modifier = Modifier
                    .clip(RoundedCornerShape(40))
                    .background(MaterialTheme.colorScheme.surface),
                onClick = { },
            ) {
                Icon(
                    painter = painterResource(R.drawable.back_icon),
                    contentDescription = stringResource(R.string.back),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "Name Surname",
                    textAlign = TextAlign.Center,
                )
            }
        }

        Image(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 20.dp)
                .clip(RoundedCornerShape(10)),
            alignment = Alignment.Center,
            painter = painterResource(R.drawable.picture_2),
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )

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
                    onClick = { },
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
                onClick = { },
            ) {
                Icon(
                    painter = painterResource(R.drawable.save_icon),
                    contentDescription = stringResource(R.string.download),
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailsPreview() {
    DetailsScreen()
}
