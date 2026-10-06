package com.example.androidworks.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.androidworks.ui.components.BottomBar
import com.example.androidworks.ui.screens.details.DetailsScreen
import com.example.androidworks.ui.screens.list.ListScreen
import com.example.androidworks.ui.screens.stub.StubScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    val showBottomBar = currentRoute != DetailsDestination.ROUTE

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomBar(
                    currentRoute = currentRoute,
                    onTabSelected = { tab ->
                        navController.navigate(tab.route) {
                            popUpTo(BottomTab.LIST.route) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomTab.LIST.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(BottomTab.LIST.route) {
                ListScreen(
                    onArtworkClick = { objectId ->
                        navController.navigate(DetailsDestination.createRoute(objectId))
                    }
                )
            }
            composable(BottomTab.PROFILE.route) {
                StubScreen(title = "Профиль", subtitle = "Экран будет реализован позже")
            }
            composable(
                route = DetailsDestination.ROUTE,
                arguments = listOf(navArgument(DetailsDestination.ARG_OBJECT_ID) {
                    type = NavType.IntType
                })
            ) { entry ->
                val objectId = entry.arguments?.getInt(DetailsDestination.ARG_OBJECT_ID) ?: 0
                DetailsScreen(
                    objectId = objectId,
                    onBackClick = { navController.popBackStack() }
                )
            }
        }
    }
}