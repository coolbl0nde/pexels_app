package com.example.feature.presentation.home

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
import com.example.feature.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarComponent(
    modifier: Modifier = Modifier
) {

    var expanded by remember {
        mutableStateOf(false)
    }

    var text by remember {
        mutableStateOf("")
    }

    SearchBar(
        inputField = {
            SearchBarDefaults.InputField(
                query = text,
                onQueryChange = { text = it},
                onSearch = {},
                expanded = expanded,
                onExpandedChange = { expanded = it },
                placeholder = { Text(text = "Search") },
                leadingIcon = { Icon(
                    painter = painterResource(R.drawable.search_icon),
                    contentDescription = "Search",
                    tint = Color(0xFFBB1020)
                ) }
            )
        },
        expanded = expanded,
        onExpandedChange = { expanded = it },
        modifier = modifier
    ) {

    }
}