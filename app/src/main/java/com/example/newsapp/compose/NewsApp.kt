package com.example.newsapp.compose

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.compose.screens.articleDetail.ArticleDetailScreen
import com.example.newsapp.compose.screens.explore.ExploreScreen
import com.example.newsapp.compose.screens.home.HomeScreen
import com.example.newsapp.compose.screens.onboarding.OnboardingScreen
import com.example.newsapp.compose.screens.setting.SettingScreen
import com.example.newsapp.data.remote.dto.ArticlesItem
import com.google.gson.Gson
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
fun NewsApp() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) { innerPadding ->
        NewsNavHost(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun NewsNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
    ) {
        composable(route = Screen.Onboarding.route) { OnboardingScreen() }
        composable(route = Screen.Home.route) {
            HomeScreen(
                onNewsClick = {
//                    navController.navigate(Screen.ArticleDetail.createRoute(it))
                    Log.d("HomeScreen", "onNewsClick: ${it.title}")
                }
            )
        }
        composable(route = Screen.Explore.route) { ExploreScreen() }
        composable(
            route = Screen.ArticleDetail.route,
            arguments = Screen.ArticleDetail.navArguments
        ) { backStackEntry ->
            val articleJson = backStackEntry.arguments?.getString("articleJson")
            articleJson?.let {
                val decodedJson = URLDecoder.decode(it, StandardCharsets.UTF_8.toString())
                val article = Gson().fromJson(decodedJson, ArticlesItem::class.java)
                ArticleDetailScreen(article = article)
            }
        }
    }
}