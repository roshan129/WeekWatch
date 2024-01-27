package com.roshanadke.weekwatch.common

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.roshanadke.weekwatch.domain.models.TrendingItem
import com.roshanadke.weekwatch.presentation.screens.DetailsScreen
import com.roshanadke.weekwatch.presentation.screens.PeopleListScreen
import com.roshanadke.weekwatch.presentation.screens.TrendingShowScreen

@Composable
fun Navigation(
    navController: NavHostController
) {

    NavHost(navController = navController, startDestination = Screen.TrendingShowsScreen.route) {
        composable(Screen.TrendingShowsScreen.route) {
            TrendingShowScreen(navController = navController)
        }
        composable(
            Screen.DetailsScreen.route,
            enterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left)
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right)
            }) { navBackStackEntry ->
            val resultItem =
                navController.previousBackStackEntry?.savedStateHandle?.get<TrendingItem>(Constants.TRENDING_ITEM)
            DetailsScreen(navController = navController, trendingItem = resultItem)
        }
        composable(BottomNavigationScreens.PeopleListScreen.route) {
            PeopleListScreen(navController)
        }
    }

}