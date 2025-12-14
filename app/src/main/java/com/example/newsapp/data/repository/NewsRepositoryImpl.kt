package com.example.newsapp.data.repository

import com.example.newsapp.data.remote.NewsApi
import com.example.newsapp.data.remote.dto.Article
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsApi: NewsApi
) : NewsRepository {

    override suspend fun getNews(): List<Article> {
        return newsApi.getNews().articles
    }
}