package com.example.newsapp.di

import com.example.newsapp.data.remote.NewsApi
import com.example.newsapp.data.repository.remote.RemoteNewsRepository
import com.example.newsapp.data.repository.remote.RemoteNewsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {
    @Provides
    @Singleton
    fun provideNewsRepository(api: NewsApi): RemoteNewsRepository {
        return RemoteNewsRepositoryImpl(api)
    }
}