package com.example.newsapp.compose.screens.articleDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.newsapp.data.remote.dto.ArticlesItem
import com.example.newsapp.util.h

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleDetailScreen(article: ArticlesItem) {
    val listState = rememberLazyListState()
    val maxHeaderHeight = 30.h

    val scrollOffset = listState.firstVisibleItemScrollOffset
    val collapsedRangePx = with(LocalDensity.current) { maxHeaderHeight.toPx() }

    val collapsedFraction = (scrollOffset / collapsedRangePx).coerceIn(0f, 1f)

    Box {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(article.urlToImage)
                .crossfade(true)
                .build(),
            contentDescription = article.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(maxHeaderHeight)
                .graphicsLayer {
                    alpha = 1f - collapsedFraction
                    translationY = -scrollOffset * 0.5f
                },
            contentScale = ContentScale.Crop
        )

        LazyColumn(
            state = listState,
            contentPadding = PaddingValues(top = maxHeaderHeight - 5.h)
        ) {
            item { ArticleContent() }
        }
    }
}

@Composable
fun ArticleContent() {
    Box(
        modifier = Modifier.fillMaxWidth()
            .height(180.h)
            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            .background(Color.Blue)
    )
}

@Preview(showBackground = true)
@Composable
fun ArticleDetailScreenPreview() {
    val mockArticle = ArticlesItem(
        author = "Jane Doe",
        title = "Major Tech Breakthrough Changes the Industry Overnight",
        description = "In a stunning development, a new technology has emerged that promises to revolutionize the industry.",
        url = "https://example.com/major-tech-breakthrough",
        urlToImage = "https://example.com/image.jpg",
        publishedAt = "2023-07-10T12:00:00Z",
        content = "In a stunning development, a new technology has emerged that promises to revolutionize the industry. ",
        source = null
    )
    ArticleDetailScreen(article = mockArticle)
}