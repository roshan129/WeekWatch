package com.roshanadke.weekwatch.common

sealed class Screen(val route: String) {

    object TrendingShowsScreen: Screen("TrendingShowsScreen")
    object DetailsScreen: Screen("DetailsScreen")

}
