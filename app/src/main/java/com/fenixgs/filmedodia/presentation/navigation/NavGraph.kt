package com.fenixgs.filmedodia.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.fenixgs.filmedodia.presentation.home.HomeScreen
import com.fenixgs.filmedodia.presentation.profile.ProfileScreen
import com.fenixgs.filmedodia.presentation.login.LoginScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(navController = navController)
        }
        composable("home") {
            HomeScreen(navController = navController)
        }
        composable("profile") {
            ProfileScreen(navController = navController)
        }

    }
}
