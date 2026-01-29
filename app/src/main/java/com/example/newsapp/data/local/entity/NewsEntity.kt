package com.example.newsapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "news",
    indices = [
        Index(value = ["url"], unique = true),
    ]
)
data class NewsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "imageUrl")
    val imageUrl: String,
    @ColumnInfo(name = "content")
    val content: String,
    @ColumnInfo(name = "author")
    val author: String,
    @ColumnInfo(name = "publishedAt")
    val publishedAt: String,
    @ColumnInfo(name = "source")
    val source: String,
    @ColumnInfo(name = "url")
    val url: String
)