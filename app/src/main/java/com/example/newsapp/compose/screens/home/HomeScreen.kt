package com.example.newsapp.compose.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.newsapp.R
import com.example.newsapp.compose.component.BaseAppBar
import com.example.newsapp.compose.screens.home.views.NewsRow
import com.example.newsapp.data.remote.dto.ArticlesItem
import com.example.newsapp.util.ResponseStatus
import com.example.newsapp.util.h
import com.example.newsapp.util.shimmer
import com.example.newsapp.util.w

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel(), onNewsClick: (ArticlesItem) -> Unit) {
    val newsState = viewModel.newsState.collectAsStateWithLifecycle().value
    val headlinesState = viewModel.topHeadlinesState.collectAsStateWithLifecycle().value

    LaunchedEffect(Unit) {
        viewModel.getNews()
        viewModel.getTopHeadlines()
    }

    Scaffold(
        topBar = {
            BaseAppBar(
                title = stringResource(R.string.app_name)
            )
        }
    ) { contentPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(horizontal = 3.w, vertical = 2.h)
                .background(MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.spacedBy(3.h)
        ) {
            item {
                when (newsState) {
                    is ResponseStatus.Loading -> LoadingUI()
                    is ResponseStatus.Error -> Text(text = newsState.message)
                    is ResponseStatus.Success -> {
                        NewsRow(
                            newsResponse = newsState.data,
                            modifier = Modifier.fillMaxWidth(),
                            onNewsClick = onNewsClick,
                            onLoadMore = { viewModel.loadMoreNews() }
                        )
                    }
                }
            }
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = stringResource(R.string.top_headlines),
                        style = MaterialTheme.typography.headlineLarge.copy(color = MaterialTheme.colorScheme.onSurface)
                    )

                    Text(
                        modifier = Modifier
                            .clip(RoundedCornerShape(5.dp))
                            .clickable(onClick = { /*TODO*/ })
                            .padding(5.dp),
                        text = stringResource(R.string.see_more),
                        style = MaterialTheme.typography.headlineSmall.copy(color = MaterialTheme.colorScheme.onPrimary)
                    )
                }
            }
            item {
                when (headlinesState) {
                    is ResponseStatus.Loading -> LoadingUI()
                    is ResponseStatus.Error -> Text(text = headlinesState.message)
                    is ResponseStatus.Success -> {
                        NewsRow(
                            newsResponse = headlinesState.data,
                            modifier = Modifier.fillMaxWidth(),
                            onNewsClick = onNewsClick,
                            onLoadMore = { viewModel.loadMoreTopHeadlines() }
                        )
                    }
                }
            }
        }
    }
}

@Preview(name = "Loading UI")
@Composable
private fun LoadingUI() {
    LazyRow(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(3.w)
    ) {
        items(10) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .width(70.w)
                    .height(30.h)
                    .shimmer()
            )
        }
    }
}