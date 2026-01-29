package com.example.newsapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.newsapp.data.local.dao.NewsDao
import com.example.newsapp.data.local.entity.NewsEntity

@Database(entities = [NewsEntity::class], version = 1)
abstract class MainDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}