package com.example.feature.presentation.home.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.core.model.FeaturedCollection
import kotlinx.collections.immutable.ImmutableList

@Composable
fun HorizontalListComponent(
    modifier: Modifier = Modifier,
    featuredCollection: ImmutableList<FeaturedCollection>,
    selectedItem: String,
    onSelectedItemChange: (String) -> Unit,
) {
    LazyRow (
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(featuredCollection) { item ->
            HorizontalListItem(
                text = item.title,
                isSelected = item.title == selectedItem,
                onClick = { onSelectedItemChange(item.title) },
            )
        }
    }
}