package com.example.newsapp.compose.screens.articleDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.local.entity.NewsEntity
import com.example.newsapp.data.remote.dto.ArticlesItem
import com.example.newsapp.data.remote.dto.toEntity
import com.example.newsapp.data.repository.local.LocalNewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleDetailViewModel @Inject constructor(
    private val localNewsRepository: LocalNewsRepository
) : ViewModel() {
    private val _articleDetail = MutableStateFlow<NewsEntity?>(null)
    val articleDetail = _articleDetail.asStateFlow()

    fun setCurrentArticle(articlesItem: ArticlesItem) {
        val localArticleDetail = articlesItem.toEntity()
        _articleDetail.value = localArticleDetail
    }

    fun addToFavorites() {
        val articleDetail = _articleDetail.value ?: return
        viewModelScope.launch {
            localNewsRepository.addToFavorites(articleDetail)
        }
    }

    fun removeFromFavorites() {
        val articleDetail = _articleDetail.value ?: return
        viewModelScope.launch {
            localNewsRepository.removeFromFavorites(articleDetail)
        }
    }

    fun isFavorite(): Flow<Boolean> {
        val articleDetail = _articleDetail.value
        return localNewsRepository.isFavorite(articleDetail?.url ?: "")
    }

    fun shareArticle() {
        val articleDetail = _articleDetail.value ?: return

    }
}