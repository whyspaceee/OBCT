package com.obcteam.obct.presentation.navigation

import android.content.res.Configuration
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

data class NavigationItem(
    val title: String,
    val route: MainScreen,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
) {
    companion object {
        val items = listOf(
            NavigationItem(
                title = "History",
                route = MainScreen.History,
                selectedIcon = Icons.Filled.DateRange,
                unselectedIcon = Icons.Outlined.DateRange
            ),
            NavigationItem(
                title = "Chat",
                route = MainScreen.Chat,
                selectedIcon = Icons.Filled.Email,
                unselectedIcon = Icons.Outlined.Email
            ),
            NavigationItem(
                title = "Settings",
                route = MainScreen.Settings,
                selectedIcon = Icons.Filled.Settings,
                unselectedIcon = Icons.Outlined.Settings
            ),

            )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationScaffold(
    rootNavController: NavHostController
) {
    val navController = rememberNavController()
    val currentBackStack = navController.currentBackStackEntryAsState()
    val screen = currentBackStack
        .value
        ?.destination
        ?.route
        ?: MainScreen.Chat.route

    val configuration = LocalConfiguration.current
    Scaffold(
        bottomBar = {
            if (Configuration.ORIENTATION_PORTRAIT == configuration.orientation) {
                NavigationBar(
                ) {
                    NavigationItem.items.map { navItem ->
                        val selected = navItem.route.route == screen
                        val icon = if (selected) navItem.selectedIcon else navItem.unselectedIcon
                        NavigationBarItem(selected = selected, onClick = {
                            if (screen != navItem.route.route) {
                                navController.navigate(navItem.route.route)
                            }
                        }, icon = {
                            Icon(imageVector = icon, contentDescription = null)
                        })
                    }
                }
            }
        }) {
        Row {
            if (Configuration.ORIENTATION_LANDSCAPE == configuration.orientation) {
                NavigationRail(
                ) {
                    NavigationItem.items.map { navItem ->
                        val selected = navItem.route.route == screen
                        val icon = if (selected) navItem.selectedIcon else navItem.unselectedIcon
                        NavigationRailItem(selected = selected, onClick = {
                            if (screen != navItem.route.route) {
                                navController.navigate(navItem.route.route)
                            }
                        }, icon = {
                            Icon(imageVector = icon, contentDescription = null)
                        })
                    }
                }
                Divider(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp)
                )
            }
            MainNavGraph(
                modifier = Modifier.padding(it),
                navController = navController,
                rootNavController = rootNavController
            )
        }
    }


}