package com.example.newsapp.compose

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.compose.screens.home.HomeScreen

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
        composable(route = Screen.Home.route) {
            HomeScreen()
        }
        composable(route = Screen.Search.route) {}
    }
}