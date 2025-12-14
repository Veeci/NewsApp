package com.example.newsapp.data.remote

import com.example.newsapp.data.remote.dto.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("everything")
    suspend fun getNews(
        @Query("q") query: String = "world",
        @Query("sortBy") sortBy: String = "publishedAt"
    ): NewsResponse
}
