package com.roshanadke.weekwatch.presentation.components

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.roshanadke.weekwatch.common.BottomNavigationScreens
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigationBar(navController: NavController) {


    val items = listOf(
        BottomNavigationScreens.HomeScreen,
        BottomNavigationScreens.PeopleListScreen,
    )

    BottomNavigation(
        backgroundColor = Color.Gray,
        contentColor = Color.White,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        items.forEach { item ->
            BottomNavigationItem(
                selected = currentDestination?.route == item.route,
                selectedContentColor = Color.Blue,
                unselectedContentColor = Color.White,
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = stringResource(id = item.resourceId)
                    )
                },
                label = {
                    Text(text = stringResource(id = item.resourceId))
                },
                onClick = {
                          navController.navigate(item.route) {
                              popUpTo(navController.graph.findStartDestination().id) {
                                  saveState = true
                              }
                              launchSingleTop = true
                              restoreState = true
                          }
                },
            )
        }
    }

}
