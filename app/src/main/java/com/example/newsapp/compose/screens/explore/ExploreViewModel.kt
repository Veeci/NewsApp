package com.example.newsapp.compose.screens.explore

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.local.dataModel.PaginatedFeedState
import com.example.newsapp.data.remote.dto.NewsResponse
import com.example.newsapp.data.repository.NewsRepository
import com.example.newsapp.util.Category
import com.example.newsapp.util.Constants
import com.example.newsapp.util.ResponseStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {
    private val _selectedCategory = MutableStateFlow(Category.entries.toTypedArray().first())
    val selectedCategory = _selectedCategory.asStateFlow()

    private val _newsList = PaginatedFeedState<NewsResponse>()
    val newsList = _newsList.state.asStateFlow()

    fun onCategorySelected(category: Category) {
        _selectedCategory.value = category
        getNewsByCategory(category)
    }

    fun getNewsByCategory(category: Category) {
        _newsList.reset()

        fetchPaginatedNews(
            newsFeed = _newsList,
        ) { page ->
            newsRepository.getTopHeadlines(
                category = category.name,
                page = page,
                pageSize = Constants.PAGE_SIZE,
            )
        }
    }

    private fun fetchPaginatedNews(
        newsFeed: PaginatedFeedState<NewsResponse>,
        fetcher: suspend (Int) -> Flow<ResponseStatus<NewsResponse>>
    ) {
        newsFeed.job?.cancel()

        newsFeed.job = viewModelScope.launch {
            newsFeed.mutex.withLock {
                fetcher(newsFeed.currentPage).collect { result ->
                    when (result) {
                        is ResponseStatus.Loading -> {
                            if (newsFeed.currentPage == 1) {
                                newsFeed.state.value = ResponseStatus.Loading
                            }
                        }

                        is ResponseStatus.Error -> {
                            newsFeed.state.value = result
                        }

                        is ResponseStatus.Success -> {
                            val oldNewsData =
                                (newsFeed.state.value as? ResponseStatus.Success)?.data?.articles.orEmpty()

                            val newNewsData = result.data.articles.orEmpty()

                            if (newNewsData.isEmpty()) {
                                newsFeed.isLastPage = true
                            } else {
                                newsFeed.currentPage++
                            }

                            newsFeed.state.value = ResponseStatus.Success(
                                result.data.copy(
                                    articles = if (newsFeed.currentPage == 2) newNewsData else oldNewsData + newNewsData
                                )
                            )
                            Log.d("ExploreViewModel", "fetchPaginatedNews: ${newsFeed.state.value}")
                        }
                    }
                }
            }
        }
    }
}