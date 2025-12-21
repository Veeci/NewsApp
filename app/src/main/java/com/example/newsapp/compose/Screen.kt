/*
 * Copyright 2023 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.newsapp.compose

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.newsapp.data.remote.dto.ArticlesItem
import com.google.gson.Gson
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

sealed class Screen(
    val route: String,
    val navArguments: List<NamedNavArgument> = emptyList()
) {
    data object Onboarding : Screen("onboarding")

    data object Home : Screen("home")

    data object Explore : Screen("explore")

    data object ArticleDetail : Screen(
        route = "articleDetail/{articleJson}",
        navArguments = listOf(navArgument("articleJson") {
            type = NavType.StringType
        })
    ) {
        fun createRoute(article: ArticlesItem) :String {
            val articleJson = Gson().toJson(article)
            val enCodedArticle = URLEncoder.encode(articleJson, StandardCharsets.UTF_8.toString())
            return "articleDetail/$enCodedArticle"
        }
    }
}