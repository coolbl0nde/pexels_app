package com.example.feature.presentation.home.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.example.core.utils.empty
import com.example.feature.presentation.home.presentation.model.FeaturedCollectionUi

@Composable
fun HorizontalListComponent(
    modifier: Modifier = Modifier,
    featuredCollections: LazyPagingItems<FeaturedCollectionUi>,
    selectedItem: String,
    onSelectedItemChange: (Int) -> Unit,
    onTextChange: (String) -> Unit,
) {

    /*LaunchedEffect(key1 = featuredCollections.itemCount) {
        if (featuredCollections.itemCount > 0 && selectedItem.isEmpty()) {
            featuredCollections[0]?.let { firstItem ->
                onSelectedItemChange(firstItem.title)
            }
        }
    }*/

    LazyRow (
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(featuredCollections.itemCount) { index ->
            val featuredCollection = featuredCollections[index]

            featuredCollection?.let {

                HorizontalListItem(
                    text = featuredCollection.title,
                    isSelected = featuredCollection.isSelected,/*featuredCollection.title == selectedItem*/
                    onClick = {
                        onTextChange(String.empty)
                        onSelectedItemChange(featuredCollection.index)
                    },
                )
            }
        }
    }
}