package com.example.newsapp.compose

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.compose.screens.articleDetail.ArticleDetailScreen
import com.example.newsapp.compose.screens.home.HomeScreen
import com.example.newsapp.compose.screens.onboarding.OnboardingScreen
import com.example.newsapp.compose.screens.setting.SettingScreen

@Composable
fun NewsApp() {
    val navController = rememberNavController()
    NewsNavHost(navController = navController)
}

@Composable
fun NewsNavHost(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(route = Screen.Onboarding.route) { OnboardingScreen() }
        composable(route = Screen.Home.route) { HomeScreen() }
        composable(route = Screen.Setting.route) { SettingScreen() }
        composable(route = Screen.ArticleDetail.route, arguments = Screen.ArticleDetail.navArguments) { ArticleDetailScreen() }
    }
}