package com.example.newsapp.data.repository

import com.example.newsapp.data.remote.NewsApi
import com.example.newsapp.data.remote.dto.NewsDto
import com.example.newsapp.util.ApiHandler
import com.example.newsapp.util.ResponseStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsApi: NewsApi
) : NewsRepository, ApiHandler {
    override suspend fun getNews(
        query: String,
        page: Int,
        pageSize: Int,
        language: String
    ): Flow<ResponseStatus<NewsDto>> {
        return flow {
            emit(handleApi {
                newsApi.getNews(
                    query = query,
                    page = page,
                    pageSize = pageSize,
                    language = language
                )
            })
        }.onStart { emit(ResponseStatus.Loading) }
            .flowOn(Dispatchers.IO)
            .catch { emit(ResponseStatus.Error(it.message ?: "Unknown error")) }
    }

    override suspend fun getTopHeadlines(
        country: String,
        category: String,
        page: Int,
        pageSize: Int
    ): Flow<ResponseStatus<NewsDto>> {
        return flow {
            emit(handleApi {
                newsApi.getTopHeadlines(
                    country = country,
                    category = category,
                    page = page,
                    pageSize = pageSize,
                )
            })
        }.onStart { emit(ResponseStatus.Loading) }
            .catch { emit(ResponseStatus.Error(it.message ?: "Unknown error")) }
            .flowOn(Dispatchers.IO)
    }

}