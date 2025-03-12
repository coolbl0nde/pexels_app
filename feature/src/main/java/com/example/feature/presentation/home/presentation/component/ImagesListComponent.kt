package com.example.feature.presentation.home.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import coil3.compose.AsyncImage
import com.example.core.model.Photo

@Composable
fun ImagesListComponent(
    modifier: Modifier = Modifier,
    photos: LazyPagingItems<Photo>,
) {
    LazyVerticalStaggeredGrid(
        modifier = modifier,
        /*columns = StaggeredGridCells.Adaptive(155.dp),*/
        columns = StaggeredGridCells.Fixed(2),
        state = rememberLazyStaggeredGridState(),
        verticalItemSpacing = 10.dp,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        content = {
            items(
                photos.itemCount,
                key = { index -> photos[index]?.id
                    ?: error("Item at index $index is null") },
            ) { index ->
                val photo = photos[index]

                photo?.let {
                    val aspectRatio = photo.width.toFloat() / photo.height

                    AsyncImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .clip(RoundedCornerShape(20))
                            .aspectRatio(aspectRatio),
                        model = photo.original,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                    )
                }
            }
        },
    )
}