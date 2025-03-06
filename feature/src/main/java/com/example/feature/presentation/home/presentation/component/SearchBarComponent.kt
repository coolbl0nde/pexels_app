package com.example.feature.presentation.home.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.feature.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarComponent(
    modifier: Modifier = Modifier,
    expanded: Boolean,
    text: String,
    onExpandedChange: (Boolean) -> Unit,
    onTextChange: (String) -> Unit,
) {
    SearchBar(
        modifier = modifier,
        inputField = {
            SearchBarDefaults.InputField(
                query = text,
                onQueryChange = onTextChange,
                onSearch = {},
                expanded = expanded,
                onExpandedChange = onExpandedChange,
                placeholder = { Text(
                    text = stringResource(R.string.search),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )},
                leadingIcon = { Icon(
                    painter = painterResource(R.drawable.search_icon),
                    contentDescription = "Search",
                    tint = MaterialTheme.colorScheme.primary
                ) },
                trailingIcon = {
                    if (text.isEmpty()) {
                        Icon(
                            modifier = Modifier.clickable { onTextChange("") },
                            painter = painterResource(R.drawable.clear_icon),
                            contentDescription = null,
                        )
                    }
                },
                colors = SearchBarDefaults.inputFieldColors(
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                ),
            )
        },
        expanded = expanded,
        onExpandedChange = onExpandedChange,
        colors = SearchBarDefaults.colors(
            containerColor = MaterialTheme.colorScheme.surface,
            dividerColor = Color.Transparent
        ),
    ) {

    }
}