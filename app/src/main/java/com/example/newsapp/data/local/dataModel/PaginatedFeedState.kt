package com.example.newsapp.data.local.dataModel

import com.example.newsapp.util.ResponseStatus
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.sync.Mutex

class PaginatedFeedState<T> {

    val state = MutableStateFlow<ResponseStatus<T>>(ResponseStatus.Loading)

    var currentPage: Int = 1
    var loadingMore: Boolean = false
    var isLastPage: Boolean = false

    val mutex = Mutex()
    var job: Job? = null

    fun reset() {
        currentPage = 1
        loadingMore = false
        isLastPage = false
        state.value = ResponseStatus.Loading
    }
}
