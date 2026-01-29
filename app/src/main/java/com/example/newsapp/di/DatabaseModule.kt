package com.example.newsapp.di

import android.content.Context
import androidx.room.Room
import com.example.newsapp.data.local.database.MainDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) : MainDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = MainDatabase::class.java,
            name = "main_database",
        ).build()
    }

    @Provides
    fun provideNewsDao(database: MainDatabase) = database.newsDao()
}