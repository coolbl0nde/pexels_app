package com.example.feature.presentation.home.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.example.core.model.FeaturedCollection
import com.example.core.utils.empty

@Composable
fun HorizontalListComponent(
    modifier: Modifier = Modifier,
    featuredCollections: LazyPagingItems<FeaturedCollection>,
    selectedItem: String,
    onSelectedItemChange: (String) -> Unit,
    onTextChange: (String) -> Unit,
) {

    LazyRow (
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(featuredCollections.itemCount) { index ->
            val featuredCollection = featuredCollections[index]

            featuredCollection?.let {

                HorizontalListItem(
                    text = featuredCollection.title,
                    isSelected = featuredCollection.title == selectedItem,
                    onClick = {
                        onTextChange(String.empty)
                        onSelectedItemChange(featuredCollection.title)
                    },
                )
            }
        }
    }
}