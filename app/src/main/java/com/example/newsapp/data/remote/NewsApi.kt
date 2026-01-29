package com.example.newsapp.data.remote

import com.example.newsapp.data.remote.dto.NewsDto
import com.example.newsapp.util.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("everything")
    suspend fun getNews(
        @Query("q") query: String = "world",
        @Query("sortBy") sortBy: String = "publishedAt",
        @Query("page") page: Int = 1,
        @Query("pageSize") pageSize: Int = Constants.PAGE_SIZE,
        @Query("language") language: String = "en",
    ): Response<NewsDto>

    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String = "us",
        @Query("category") category: String = "general",
        @Query("page") page: Int = 1,
        @Query("pageSize") pageSize: Int = Constants.PAGE_SIZE,
    ) : Response<NewsDto>
}
