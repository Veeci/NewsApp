package com.example.newsapp.util

sealed class ResponseStatus<out T> {
    data class Success<out T>(val data: T) : ResponseStatus<T>() {
        fun fetchData(): T = data
    }

    data class Error(val message: String = "", val errorCode: Int = 500) : ResponseStatus<Nothing>()

    data object Loading : ResponseStatus<Nothing>()
}
