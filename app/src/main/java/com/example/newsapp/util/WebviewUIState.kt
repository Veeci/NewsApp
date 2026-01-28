package com.example.newsapp.util

sealed class WebviewUIState {
    object Loading : WebviewUIState()
    object Success: WebviewUIState()
    data class Error(val message: String): WebviewUIState()
}