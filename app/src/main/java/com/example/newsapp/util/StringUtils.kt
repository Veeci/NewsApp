package com.example.newsapp.util

object StringUtils {
    fun uppercaseFirstLetter(input: String): String {
        if (input.isEmpty()) {
            return input
        }
        return input.first().uppercase() + input.substring(1)
    }
}