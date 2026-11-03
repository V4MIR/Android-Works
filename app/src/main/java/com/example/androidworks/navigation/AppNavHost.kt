package com.example.androidworks.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.androidworks.ui.badge.BadgeViewModel
import com.example.androidworks.ui.components.BottomBar
import com.example.androidworks.ui.screens.details.DetailsScreen
import com.example.androidworks.ui.screens.favorites.FavoritesScreen
import com.example.androidworks.ui.screens.filter.FilterScreen
import com.example.androidworks.ui.screens.list.ListScreen
import com.example.androidworks.ui.screens.list.ListViewModel
import com.example.androidworks.ui.screens.stub.StubScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    val badgeViewModel: BadgeViewModel = hiltViewModel()
    val showBadge by badgeViewModel.showBadge.collectAsState()

    val showBottomBar = currentRoute in listOf(
        BottomTab.LIST.route,
        BottomTab.FAVORITES.route,
        BottomTab.PROFILE.route
    )

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomBar(
                    currentRoute = currentRoute,
                    showBadge = showBadge,
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
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = BottomTab.LIST.route,
            modifier = Modifier.padding(padding)
        ) {
            composable(BottomTab.LIST.route) { entry ->
                val listViewModel: ListViewModel = hiltViewModel()
                val reloadFlag = entry.savedStateHandle
                    .getStateFlow("reload", false)
                    .collectAsState()
                    .value

                LaunchedEffect(reloadFlag) {
                    if (reloadFlag) {
                        listViewModel.load()
                        entry.savedStateHandle["reload"] = false
                    }
                }

                ListScreen(
                    viewModel = listViewModel,
                    onArtworkClick = { id ->
                        navController.navigate(DetailsDestination.createRoute(id))
                    },
                    onOpenFilters = {
                        navController.navigate(FilterDestination.ROUTE)
                    }
                )
            }

            composable(BottomTab.FAVORITES.route) {
                FavoritesScreen(
                    onArtworkClick = { id ->
                        navController.navigate(DetailsDestination.createRoute(id))
                    }
                )
            }

            composable(BottomTab.PROFILE.route) {
                StubScreen(title = "Профиль", subtitle = "Будет позже")
            }

            composable(FilterDestination.ROUTE) {
                FilterScreen(
                    onDone = {

                        navController
                            .getBackStackEntry(BottomTab.LIST.route)
                            .savedStateHandle["reload"] = true
                        navController.popBackStack()
                    }
                )
            }

            composable(
                route = DetailsDestination.ROUTE,
                arguments = listOf(navArgument(DetailsDestination.ARG_OBJECT_ID) {
                    type = NavType.IntType
                })
            ) { entry ->
                DetailsScreen(
                    objectId = entry.arguments
                        ?.getInt(DetailsDestination.ARG_OBJECT_ID) ?: 0,
                    onBackClick = { navController.popBackStack() }
                )
            }
        }
    }
}