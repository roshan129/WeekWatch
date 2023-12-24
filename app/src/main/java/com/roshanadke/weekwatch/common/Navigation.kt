package com.roshanadke.weekwatch.common

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.roshanadke.weekwatch.presentation.screens.DetailsScreen
import com.roshanadke.weekwatch.presentation.screens.TrendingShowScreen

@Composable
fun Navigation(
    navController: NavHostController
) {

    NavHost(navController = navController, startDestination = Screen.TrendingShowsScreen.route) {
        composable(Screen.TrendingShowsScreen.route) {
            TrendingShowScreen(navController = navController)
        }
        composable(Screen.DetailsScreen.route) {
            DetailsScreen(navController = navController)
        }
    }


}