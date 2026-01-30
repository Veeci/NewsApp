package com.example.newsapp.compose.screens.articleDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.newsapp.R
import com.example.newsapp.compose.screens.articleDetail.views.BottomBar
import com.example.newsapp.compose.screens.articleDetail.views.ContentWebview
import com.example.newsapp.data.remote.dto.ArticlesItem
import com.example.newsapp.util.h

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleDetailScreen(
    viewModel: ArticleDetailViewModel = hiltViewModel(),
    article: ArticlesItem,
    onBackClick: () -> Unit
) {
    val context = LocalDensity.current
    val listState = rememberLazyListState()
    val maxHeaderHeight = 30.h

    val collapsedFraction by remember {
        derivedStateOf {
            val scrollOffset = listState.firstVisibleItemScrollOffset
            val collapsedRangePx =
                with(context) { maxHeaderHeight.toPx() }

            (scrollOffset / collapsedRangePx).coerceIn(0f, 1f)
        }
    }

    LaunchedEffect(article) {
        viewModel.setCurrentArticle(article)
    }

    val isFavorite by viewModel
        .isFavorite()
        .collectAsStateWithLifecycle(initialValue = false)

    Scaffold(
        containerColor = MaterialTheme.colorScheme.primary,
        bottomBar = {
            BottomBar(
                isFavorite,
                onToggleFavourite = { isChecked ->
                    if (isChecked) viewModel.addToFavorites() else viewModel.removeFromFavorites()
                },
                onShareArticle = { viewModel.shareArticle() },
            )
        }
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .padding(top = contentPadding.calculateTopPadding())
                .padding(top = 2.h)
        ) {
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
                        translationY = -collapsedFraction * maxHeaderHeight.toPx() * 0.5f
                    },
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.img_news_placeholder),
                error = painterResource(R.drawable.img_news_error)
            )

            LazyColumn(
                state = listState,
                contentPadding = PaddingValues(
                    top = maxHeaderHeight - 5.h,
                    bottom = contentPadding.calculateBottomPadding()
                )
            ) {
                item { ArticleContent(article) }
            }

            IconButton(onClick = onBackClick) {
                Icon(
                    modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape)
                        .background(Color.Gray.copy(alpha = 0.2f))
                        .padding(5.dp),
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                )
            }
        }
    }
}

@Composable
private fun ArticleContent(article: ArticlesItem) {
    val articleUrl = article.url ?: ""
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            .background(MaterialTheme.colorScheme.primary)
            .padding(10.dp)
    ) {
        ContentWebview(articleUrl)
    }
}

@Preview(showBackground = true)
@Composable
private fun ArticleDetailScreenPreview() {
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
    ArticleDetailScreen(article = mockArticle, onBackClick = {})
}