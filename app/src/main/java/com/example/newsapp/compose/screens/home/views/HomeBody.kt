package com.example.newsapp.compose.screens.home.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.newsapp.data.remote.dto.ArticlesItem
import com.example.newsapp.data.remote.dto.NewsResponse
import com.example.newsapp.util.h
import com.example.newsapp.util.w

@Composable
fun HomeBody(
    newsResponse: NewsResponse,
    modifier: Modifier = Modifier,
    onNewsClick: (ArticlesItem) -> Unit
) {
    val articleList = newsResponse.articles ?: emptyList()

    return Column(
        modifier = modifier.padding(horizontal = 2.w, vertical = 3.h)
            .background(MaterialTheme.colorScheme.background)
    ) {
        LazyRow(
            modifier = modifier.fillMaxWidth()
        ) {
            items(items = articleList) { article ->
                ArticleItemView(
                    article = article,
                    onNewsClick = { onNewsClick(article) })
            }
        }
    }
}