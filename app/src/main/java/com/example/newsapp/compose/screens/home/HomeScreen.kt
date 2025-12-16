package com.example.newsapp.compose.screens.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.newsapp.R
import com.example.newsapp.compose.component.BaseAppBar
import com.example.newsapp.compose.screens.home.views.HomeBody
import com.example.newsapp.data.remote.dto.ArticlesItem
import com.example.newsapp.util.ResponseStatus

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel(), onNewsClick: (ArticlesItem) -> Unit) {
    val newsState = viewModel.newsState.collectAsState().value

    LaunchedEffect(Unit) {
        viewModel.getNews()
    }

    Scaffold(
        topBar = {
            BaseAppBar(
                title = stringResource(R.string.app_name)
            )
        }
    ) { contentPadding ->
        when (newsState) {
            is ResponseStatus.Loading -> CircularProgressIndicator()
            is ResponseStatus.Error -> Text(text = newsState.message)
            is ResponseStatus.Success -> {
                HomeBody(
                    newsResponse = newsState.data,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(contentPadding),

                ) { article ->
                    onNewsClick(article)
                }
            }
        }
    }
}
