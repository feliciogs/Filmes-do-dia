package com.fenixgs.filmedodia.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fenixgs.filmedodia.presentation.home.HomeScreen
import com.fenixgs.filmedodia.presentation.profile.ProfileScreen
import com.fenixgs.filmedodia.presentation.profile.WatchedMoviesScreen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun MainScreen(rootNavController: NavHostController) {
    val bottomNavItems = listOf(
        BottomNavItem.Home,
        BottomNavItem.Profile,
        BottomNavItem.Logout
    )
    var selectedTab by remember { mutableStateOf(0) }

    val navController = rememberNavController() // NavController interno para home/profile

    Scaffold(
        bottomBar = {
            NavigationBar {
                bottomNavItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = { Icon(painterResource(id = item.iconResId), contentDescription = null) },
                        label = { Text(stringResource(id = item.titleResId)) },
                        selected = selectedTab == index,
                        onClick = {
                            selectedTab = index
                            if (item is BottomNavItem.Logout) {
                                FirebaseAuth.getInstance().signOut()
                                rootNavController.navigate("login") {
                                    popUpTo(0)
                                }
                            } else {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            NavHost(
                navController = navController,
                startDestination = BottomNavItem.Home.route
            ) {
                composable(BottomNavItem.Home.route) {
                    HomeScreen(navController)
                }
                composable(BottomNavItem.Profile.route) {
                    ProfileScreen(navController)
                }
                composable("watchedMovies") {
                    WatchedMoviesScreen(navController)
                }
            }
        }
    }
}
