package com.example.newsapp.compose.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.local.dataModel.PaginatedFeedState
import com.example.newsapp.data.remote.dto.NewsResponse
import com.example.newsapp.data.repository.NewsRepository
import com.example.newsapp.util.Constants
import com.example.newsapp.util.Constants.MAX_RESULTS
import com.example.newsapp.util.ResponseStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {

    // 🔹 Feeds
    private val newsFeed = PaginatedFeedState<NewsResponse>()
    val newsState: StateFlow<ResponseStatus<NewsResponse>> = newsFeed.state.asStateFlow()

    private val headlinesFeed = PaginatedFeedState<NewsResponse>()
    val topHeadlinesState: StateFlow<ResponseStatus<NewsResponse>> = headlinesFeed.state.asStateFlow()

    fun getNews(
        query: String = "world",
        language: String = "en",
        pageSize: Int = Constants.PAGE_SIZE
    ) {
        newsFeed.reset()

        fetchPaginatedFeed(
            feed = newsFeed,
            pageSize = pageSize
        ) { page ->
            newsRepository.getNews(
                query = query,
                page = page,
                pageSize = pageSize,
                language = language
            )
        }
    }

    fun loadMoreNews(
        query: String = "world",
        language: String = "en",
        pageSize: Int = Constants.PAGE_SIZE
    ) {
        loadMore(
            feed = newsFeed,
            pageSize = pageSize
        ) { page ->
            newsRepository.getNews(
                query = query,
                page = page,
                pageSize = pageSize,
                language = language
            )
        }
    }

    fun getTopHeadlines(
        country: String = "us",
        category: String = "general",
        pageSize: Int = Constants.PAGE_SIZE
    ) {
        headlinesFeed.reset()

        fetchPaginatedFeed(
            feed = headlinesFeed,
            pageSize = pageSize
        ) { page ->
            newsRepository.getTopHeadlines(
                country = country,
                category = category,
                page = page,
                pageSize = pageSize
            )
        }
    }

    fun loadMoreTopHeadlines(
        country: String = "us",
        category: String = "general",
        pageSize: Int = Constants.PAGE_SIZE
    ) {
        loadMore(
            feed = headlinesFeed,
            pageSize = pageSize
        ) { page ->
            newsRepository.getTopHeadlines(
                country = country,
                category = category,
                page = page,
                pageSize = pageSize
            )
        }
    }

    private fun fetchPaginatedFeed(
        feed: PaginatedFeedState<NewsResponse>,
        pageSize: Int,
        fetcher: suspend (Int) -> Flow<ResponseStatus<NewsResponse>>
    ) {
        feed.job?.cancel()

        feed.job = viewModelScope.launch {
            feed.mutex.withLock {
                fetcher(feed.currentPage).collect { result ->
                    when (result) {
                        is ResponseStatus.Loading -> {
                            if (feed.currentPage == 1) {
                                feed.state.value = ResponseStatus.Loading
                            }
                        }

                        is ResponseStatus.Success -> {
                            val oldArticles =
                                (feed.state.value as? ResponseStatus.Success)
                                    ?.data
                                    ?.articles
                                    .orEmpty()

                            val newArticles = result.data.articles.orEmpty()

                            if (newArticles.isEmpty()) {
                                feed.isLastPage = true
                            } else {
                                feed.currentPage++
                            }

                            feed.state.value = ResponseStatus.Success(
                                result.data.copy(
                                    articles =
                                        if (feed.currentPage == 2)
                                            newArticles
                                        else
                                            oldArticles + newArticles
                                )
                            )
                        }

                        is ResponseStatus.Error -> {
                            feed.state.value = result
                        }
                    }
                }
            }
        }
    }

    private fun loadMore(
        feed: PaginatedFeedState<NewsResponse>,
        pageSize: Int,
        fetcher: suspend (Int) -> Flow<ResponseStatus<NewsResponse>>
    ) {
        if (feed.loadingMore || feed.isLastPage) return

        if (isExceedingLimit(feed.currentPage, pageSize)) {
            feed.isLastPage = true
            return
        }

        feed.loadingMore = true

        fetchPaginatedFeed(
            feed = feed,
            pageSize = pageSize,
            fetcher = fetcher
        )

        feed.loadingMore = false
    }

    private fun isExceedingLimit(page: Int, pageSize: Int): Boolean {
        val maxPage = MAX_RESULTS / pageSize
        return page > maxPage
    }
}