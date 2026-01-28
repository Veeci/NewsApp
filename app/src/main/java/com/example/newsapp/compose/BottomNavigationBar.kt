package com.example.newsapp.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.newsapp.R
import com.example.newsapp.util.h

data class BottomNavItem(
    val label: String,
    val icon: Int,
    val selectedIcon: Int,
    val route: String,
)

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavItem(
            label = stringResource(R.string.home),
            icon = R.drawable.ic_home,
            selectedIcon = R.drawable.ic_home_selected,
            route = Screen.Home.route
        ),
        BottomNavItem(
            label = stringResource(R.string.explore),
            icon = R.drawable.ic_explore,
            selectedIcon = R.drawable.ic_explore_selected,
            route = Screen.Explore.route
        )
    )

    NavigationBar(
        modifier = Modifier
            .padding(vertical = 1.h)
            .clip(AbsoluteRoundedCornerShape(topLeft = 40.dp, topRight = 40.dp)),
        containerColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.9f),
        windowInsets = NavigationBarDefaults.windowInsets
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            val isSelected = currentRoute == item.route
            val icon = if (isSelected) item.selectedIcon else item.icon
            NavigationBarItem(
                icon = { Icon(painter = painterResource(icon), contentDescription = item.label) },
                selected = isSelected,
                label = {
                    AnimatedVisibility(visible = isSelected) {
                        Text(text = item.label)
                    }
                },
                onClick = {
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = MaterialTheme.colorScheme.onSurface,
                    selectedIconColor = MaterialTheme.colorScheme.surface,
                    selectedTextColor = MaterialTheme.colorScheme.onSurface,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurface,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurface
                ),
            )
        }
    }
}