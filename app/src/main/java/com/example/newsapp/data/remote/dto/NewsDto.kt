package com.example.newsapp.data.remote.dto

import android.os.Parcelable
import com.example.newsapp.data.local.entity.NewsEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewsDto(
    val totalResults: Int? = null,
    val articles: List<ArticlesItem>? = null,
    val status: String? = null
) : Parcelable

@Parcelize
data class ArticlesItem(
    val publishedAt: String? = null,
    val author: String? = null,
    val urlToImage: String? = null,
    val description: String? = null,
    val source: Source? = null,
    val title: String? = null,
    val url: String? = null,
    val content: String? = null
) : Parcelable

@Parcelize
data class Source(
    val name: String? = null,
    val id: String? = null
) : Parcelable

fun ArticlesItem.toEntity(): NewsEntity {
    return NewsEntity(
        title = title.orEmpty(),
        description = description.orEmpty(),
        imageUrl = urlToImage.orEmpty(),
        content = content.orEmpty(),
        author = author.orEmpty(),
        publishedAt = publishedAt.orEmpty(),
        source = source?.name.orEmpty(),
        url = url.orEmpty()
    )
}