package com.example.feature.presentation.bookmarks.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.LazyPagingItems
import coil3.compose.AsyncImage
import com.example.core.model.Photo

@Composable
fun BookmarksListComponent(
    modifier: Modifier = Modifier,
    bookmarks: LazyPagingItems<Photo>,
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
                bookmarks.itemCount,
            ) { index ->
                val bookmark = bookmarks[index]

                bookmark?.let {
                    val aspectRatio = bookmark.width.toFloat() / bookmark.height

                    Box (
                        modifier = Modifier.clip(RoundedCornerShape(20)),
                        contentAlignment = Alignment.Center,
                    ) {
                        AsyncImage(
                            modifier = Modifier
                                .aspectRatio(aspectRatio)
                                .clickable {
                                    onPhotoClick(bookmark.id)
                                },
                            model = bookmark.original,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            placeholder = ColorPainter(MaterialTheme.colorScheme.surface),
                            error = ColorPainter(MaterialTheme.colorScheme.surface),
                        )
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .fillMaxWidth()
                                .height(35.dp)
                                .background(Color(0x66000000)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                modifier = Modifier.padding(horizontal = 10.dp),
                                text = bookmark.photographer,
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                fontSize = 14.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }
                    }

                }
            }
        },
    )
}