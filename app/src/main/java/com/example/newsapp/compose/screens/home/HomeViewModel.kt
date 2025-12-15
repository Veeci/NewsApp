package com.example.newsapp.compose.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.R
import com.example.newsapp.data.remote.dto.NewsResponse
import com.example.newsapp.data.repository.NewsRepository
import com.example.newsapp.util.Constants
import com.example.newsapp.util.ResponseStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {

    private val _newsState = MutableStateFlow<ResponseStatus<NewsResponse>>(ResponseStatus.Loading)
    val newsState: StateFlow<ResponseStatus<NewsResponse>> = _newsState.asStateFlow()

    private val _topHeadlinesState = MutableStateFlow<ResponseStatus<NewsResponse>>(ResponseStatus.Loading)
    val topHeadlinesState: StateFlow<ResponseStatus<NewsResponse>> = _topHeadlinesState.asStateFlow()

    fun getNews(
        query: String = "world",
        page: Int = 1,
        pageSize: Int = Constants.PAGE_SIZE,
        language: String = "en"
    ) {
        viewModelScope.launch {
            newsRepository.getNews(
                query = query,
                page = page,
                pageSize = pageSize,
                language = language
            ).collect { data ->
                _newsState.value = data
            }
        }
    }

    fun getTopHeadlines(
        country: String = "us",
        category: String = "general",
        page: Int = 1,
        pageSize: Int = Constants.PAGE_SIZE
    ) {
        viewModelScope.launch {
            newsRepository.getTopHeadlines(
                country = country,
                category = category,
                page = page,
                pageSize = pageSize
            ).collect { data ->
                _topHeadlinesState.value = data
            }
        }
    }
}