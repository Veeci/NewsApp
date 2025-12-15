package com.example.newsapp.data.repository

import com.example.newsapp.data.remote.dto.NewsResponse
import com.example.newsapp.util.Constants
import com.example.newsapp.util.ResponseStatus
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getNews(
        query: String = "world",
        page: Int = 1,
        pageSize: Int = Constants.PAGE_SIZE,
        language: String = "en",
    ): Flow<ResponseStatus<NewsResponse>>

    suspend fun getTopHeadlines(
        country: String = "us",
        category: String = "general",
        page: Int = 1,
        pageSize: Int = Constants.PAGE_SIZE,
    ): Flow<ResponseStatus<NewsResponse>>
}