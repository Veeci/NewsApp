package com.example.newsapp.compose.screens.explore

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.newsapp.R
import com.example.newsapp.compose.component.BaseAppBar
import com.example.newsapp.compose.screens.explore.views.ArticleItem
import com.example.newsapp.compose.screens.explore.views.CategoryItem
import com.example.newsapp.compose.screens.explore.views.FirstArticleItem
import com.example.newsapp.util.Category
import com.example.newsapp.util.ResponseStatus
import com.example.newsapp.util.h
import com.example.newsapp.util.shimmer
import com.example.newsapp.util.w

@Composable
fun ExploreScreen(viewModel: ExploreViewModel = hiltViewModel()) {
    val selectedCategory = viewModel.selectedCategory.collectAsState().value
    val newsState = viewModel.newsList.collectAsState().value

    LaunchedEffect(Unit) {
        viewModel.getNewsByCategory(selectedCategory)
    }

    Scaffold(
        topBar = {
            BaseAppBar(
                title = stringResource(R.string.explore),
                actions = {
                    IconButton({}) {
                        Icon(Icons.Filled.Search, contentDescription = "Search Icon")
                    }
                }
            )
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(contentPadding)
                .padding(horizontal = 3.w, vertical = 2.h)
        ) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(3.w)
            ) {
                items(items = Category.entries.toTypedArray()) { category ->
                    CategoryItem(
                        isSelecting = category == selectedCategory,
                        category = category,
                        onClick = { viewModel.onCategorySelected(it) },
                    )
                }
            }
            Spacer(modifier = Modifier.height(height = 2.h))
            when (newsState) {
                is ResponseStatus.Loading -> LoadingUI()
                is ResponseStatus.Error -> {}
                is ResponseStatus.Success -> {
                    val news = newsState.data.articles.orEmpty()

                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(3.h)
                    ) {
                        itemsIndexed(news) { index, article ->
                            if (index == 0) {
                                FirstArticleItem(article = article, onClick = {})
                            } else {
                                ArticleItem(article = article, onClick = {})
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(name = "Loading UI")
@Composable
private fun LoadingUI() {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(3.w)
    ) {
        items(10) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .fillMaxWidth()
                    .height(20.h)
                    .shimmer()
            )
        }
    }
}