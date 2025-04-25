package com.example.feature.presentation.home.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import coil3.compose.AsyncImage
import coil3.compose.SubcomposeAsyncImage
import com.example.core.model.Photo
import com.example.core.utils.bouncingClickable
import com.valentinilk.shimmer.shimmer
import kotlinx.coroutines.delay

@Composable
fun ImagesListComponent(
    modifier: Modifier = Modifier,
    photos: LazyPagingItems<Photo>,
    onPhotoClick: (Long) -> Unit,
) {
    LazyVerticalStaggeredGrid(
        modifier = modifier,
        columns = StaggeredGridCells.Fixed(2),
        state = rememberLazyStaggeredGridState(),
        verticalItemSpacing = 10.dp,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        content = {
            items(
                photos.itemCount,
            ) { index ->
                val photo = photos[index]

                photo?.let {
                    val aspectRatio = photo.width.toFloat() / photo.height

                    SubcomposeAsyncImage (
                        modifier = Modifier
                            .bouncingClickable{ onPhotoClick(photo.id) }
                            .clip(RoundedCornerShape(20))
                            .aspectRatio(aspectRatio),
                        model = photo.original,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        loading = {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .shimmer()
                                    .background(MaterialTheme.colorScheme.secondaryContainer)
                            )
                        },
                        error = {
                            ColorPainter(MaterialTheme.colorScheme.secondaryContainer)
                        },
                    )
                }
            }
        },
    )
}