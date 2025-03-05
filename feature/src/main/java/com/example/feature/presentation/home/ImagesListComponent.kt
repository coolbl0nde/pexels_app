package com.example.feature.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.feature.R

@Composable
fun ImagesListComponent(
    modifier: Modifier = Modifier
) {

    val imageList = listOf(
        R.drawable.picture_1,
        R.drawable.picture_2,
        R.drawable.picture_3,
        R.drawable.picture_4,
        R.drawable.picture_5,
        R.drawable.picture_6
    )

    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(155.dp),
        verticalItemSpacing = 10.dp,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        content = {
            items(imageList) { imageRes ->
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(RoundedCornerShape(20))
                )
            }
        },
        modifier = modifier
    )
}