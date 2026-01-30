package com.example.newsapp.data.repository.local

import com.example.newsapp.data.local.entity.NewsEntity
import kotlinx.coroutines.flow.Flow

interface LocalNewsRepository {
    suspend fun getAllNews(): Flow<List<NewsEntity>>
    fun isFavorite(url: String): Flow<Boolean>
    suspend fun addToFavorites(news: NewsEntity)
    suspend fun removeFromFavorites(news: NewsEntity)
}