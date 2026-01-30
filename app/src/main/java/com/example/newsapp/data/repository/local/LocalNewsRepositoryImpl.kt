package com.example.newsapp.data.repository.local

import android.util.Log
import com.example.newsapp.data.local.dao.NewsDao
import com.example.newsapp.data.local.entity.NewsEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class LocalNewsRepositoryImpl @Inject constructor(
    private val newsDao: NewsDao
) : LocalNewsRepository {
    override suspend fun getAllNews(): Flow<List<NewsEntity>> {
        return flow {
            emit(newsDao.getAllNews())
        }.flowOn(Dispatchers.IO)
            .catch { err -> Log.e("LocalNewsRepositoryImpl", "getAllNews: ${err.message}") }
            .onStart { emit(emptyList()) }
    }

    override fun isFavorite(url: String) : Flow<Boolean> {
        return newsDao.isFavorite(url)
    }

    override suspend fun addToFavorites(news: NewsEntity) {
        newsDao.addToFavorites(news)
    }

    override suspend fun removeFromFavorites(news: NewsEntity) {
        newsDao.removeFromFavorites(news)
    }
}