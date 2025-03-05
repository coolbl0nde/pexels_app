package com.example.feature.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HorizontalListComponent(
    modifier: Modifier = Modifier
) {
    val featuredCollection = listOf("Ice", "Watches", "Drawing", "Dog", "Cat")
    var selectedItem by remember {
        mutableStateOf(featuredCollection.first())
    }

    LazyRow (
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(featuredCollection) { item ->
            HorizontalListItem(
                text = item,
                isSelected = item == selectedItem,
                onClick = {selectedItem = item}
            )
        }
    }
}