package com.example.feature.presentation.bookmarks.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
            is LoadState.Error -> {
                val error = (bookmarks.loadState.refresh as LoadState.Error).error

                if (error is NullPointerException) {
                    Text(text = "ПУСТО")
                }
                Text(text = "ПУСТО")

            }
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
                BookmarksListComponent(
                    modifier = Modifier.padding( horizontal = 20.dp ),
                    bookmarks = bookmarks,
                    onPhotoClick = onPhotoClick,
                )
            }
        }

        /*PullRefreshIndicator(
            refreshing = refreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )*/
    }
}