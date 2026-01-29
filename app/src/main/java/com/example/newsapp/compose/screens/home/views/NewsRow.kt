package com.example.newsapp.compose.screens.home.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.newsapp.data.remote.dto.ArticlesItem
import com.example.newsapp.data.remote.dto.NewsDto
import com.example.newsapp.util.h
import com.example.newsapp.util.w
import kotlinx.coroutines.flow.map

@Composable
fun NewsRow(
    newsDto: NewsDto,
    modifier: Modifier = Modifier,
    onNewsClick: (ArticlesItem) -> Unit,
    onLoadMore: () -> Unit,
) {
    val articleList = newsDto.articles ?: emptyList()
    val rowState = rememberLazyListState()

    LaunchedEffect(rowState) {
        snapshotFlow { rowState.layoutInfo.visibleItemsInfo }
            .map { visibleItems ->
                val lastVisibleIndex = visibleItems.lastOrNull()?.index ?: -1
                val totalItems = rowState.layoutInfo.totalItemsCount
                lastVisibleIndex to totalItems
            }
            .collect { (lastVisibleIndex, totalItems) ->
                if (articleList.isNotEmpty() && lastVisibleIndex >= totalItems - 1) {
                    onLoadMore()
                }
            }
    }


    LazyRow(
        state = rowState,
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(3.w)
    ) {
        items(items = articleList) { article ->
            ArticleItemView(
                article = article,
                onNewsClick = { onNewsClick(article) })
        }

        item {
            Box(
                modifier = Modifier
                    .fillParentMaxHeight()
                    .defaultMinSize(minHeight = 20.h)
                    .padding(vertical = 3.h),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.onTertiary,
                )
            }
        }
    }
}