package com.example.feature.presentation.bookmarks.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.core.utils.empty
import com.example.feature.R
import com.example.feature.presentation.bookmarks.presentation.component.BookmarksListComponent
import com.example.feature.presentation.home.presentation.component.EmptyStateComponent
import com.example.feature.presentation.home.presentation.component.ImagesListComponent
import com.example.feature.presentation.home.presentation.component.NetworkStubComponent
import okio.IOException

@Composable
fun BookmarksScreen(
    onPhotoClick: (Long) -> Unit,
    onExploreClick: () -> Unit,
    viewModel: BookmarksViewModel = hiltViewModel(),
) {

    val bookmarks = viewModel.bookmarks.collectAsLazyPagingItems()

    Column(
        modifier = Modifier.Companion.fillMaxSize(),
        horizontalAlignment = Alignment.Companion.CenterHorizontally,
    ) {
        Text(
            modifier = Modifier.padding(vertical = 25.dp),
            text = stringResource(R.string.bookmarks),
            fontWeight = FontWeight(700),
            fontSize = 18.sp,
        )


        when (bookmarks.loadState.refresh) {
            is LoadState.Loading -> {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.Top,
                ) {
                    LinearProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth(),
                        trackColor = MaterialTheme.colorScheme.surface,
                    )
                }
            }
            else -> {

                if (bookmarks.itemSnapshotList.isNotEmpty()) {
                    BookmarksListComponent(
                        modifier = Modifier.padding( horizontal = 20.dp ),
                        bookmarks = bookmarks,
                        onPhotoClick = onPhotoClick,
                    )
                } else {

                    Column (
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(text = stringResource(R.string.you_haven_t_saved_anything_yet))

                        TextButton(
                            onClick = onExploreClick
                        ) {
                            Text(
                                text = stringResource(R.string.explore),
                                fontSize = 18.sp,
                            )
                        }
                    }
                }


            }
        }
    }
}