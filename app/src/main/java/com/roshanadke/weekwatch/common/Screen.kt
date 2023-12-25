package com.roshanadke.weekwatch.common

sealed class Screen(val route: String) {

    object TrendingShowsScreen : Screen("TrendingShowsScreen")
    object DetailsScreen : Screen("DetailsScreen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}


