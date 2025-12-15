package com.example.newsapp.util

import retrofit2.HttpException
import retrofit2.Response

interface ApiHandler {
    suspend fun <T : Any> handleApi(execute: suspend () -> Response<T>): ResponseStatus<T> {
        return try {
            val response = execute()

            if (response.isSuccessful) {
                response.body()?.let {
                    ResponseStatus.Success(it)
                } ?: ResponseStatus.Error(
                    message = "Empty response body",
                    errorCode = response.code(),
                )
            } else {
                ResponseStatus.Error(
                    message = response.errorBody()?.string() ?: "API error",
                    errorCode = response.code(),
                )
            }
        } catch (e: HttpException) {
            ResponseStatus.Error(
                message = e.message ?: "HTTPException",
                errorCode = e.code(),
            )
        } catch (e: Throwable) {
            ResponseStatus.Error(
                message = e.message ?: "Unexpected error",
                errorCode = 500,
            )
        }
    }
}
