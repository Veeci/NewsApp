package com.example.newsapp.data.repository

import com.example.newsapp.data.remote.dto.Article

interface NewsRepository {

    suspend fun getNews(): List<Article>
}