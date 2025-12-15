package com.example.newsapp.compose.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.newsapp.ui.NewsAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseAppBar(
    title: String,
    actions: @Composable () -> Unit = {},
) {
    return TopAppBar(
        title = { Text(title) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            titleContentColor = MaterialTheme.colorScheme.onSurface,
            actionIconContentColor = MaterialTheme.colorScheme.onSurface,
        ),
        actions = { actions() }
    )
}

@Preview(showBackground = true)
@Composable
fun BaseAppBarPreview() {
    NewsAppTheme {
        BaseAppBar(title = "Preview", actions = {
            IconButton({}) {
                Icon(Icons.Filled.Search, contentDescription = "Search Icon")
            }
        })
    }
}