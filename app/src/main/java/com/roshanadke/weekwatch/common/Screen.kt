package com.roshanadke.weekwatch.common

sealed class Screen(val route: String) {

    object TrendingShowsScreen : Screen("TrendingShowsScreen")
    object DetailsScreen : Screen("DetailsScreen")
    object PeopleListScreen : Screen("PeopleListScreen")
    object PeopleDetailsScreen : Screen("PeopleDetailsScreen")
}


