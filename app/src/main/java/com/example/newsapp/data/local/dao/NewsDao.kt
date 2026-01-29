package com.example.newsapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsapp.data.local.entity.NewsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {
    @Query("SELECT * FROM news")
    fun getAllNews(): Flow<List<NewsEntity>>

    @Query(
        """
    SELECT EXISTS(
        SELECT 1 FROM news WHERE url = :url
    )
"""
    )
    suspend fun isFavorite(url: String): Boolean


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavorites(news: NewsEntity)

    @Delete
    suspend fun removeFromFavorites(news: NewsEntity)
}