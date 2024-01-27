package com.roshanadke.weekwatch.common

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.People
import androidx.compose.ui.graphics.vector.ImageVector
import com.roshanadke.weekwatch.R

sealed class BottomNavigationScreens(val route: String, @StringRes val resourceId: Int, val icon: ImageVector) {

    object HomeScreen: BottomNavigationScreens(Screen.TrendingShowsScreen.route, R.string.home, Icons.Default.Home)
    object PeopleListScreen: BottomNavigationScreens("PeopleListScreen", R.string.people, Icons.Default.People)

}