package com.example.feature.presentation.home.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.feature.R

@Composable
fun EmptyStateComponent(
    onSearch: () -> Unit,
    onTextChange: () -> Unit,
) {

    Column (
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {

        Text(
            text = stringResource(R.string.no_results_found),
            fontSize = 14.sp,
        )

        TextButton(
            onClick = {
                onSearch()
                onTextChange()
            }
        ) {
            Text(
                text = stringResource(R.string.explore),
                color = MaterialTheme.colorScheme.primary,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
            )
        }

    }
}