package com.fenixgs.filmedodia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fenixgs.filmedodia.data.repository.UserPreferencesRepository
import com.fenixgs.filmedodia.presentation.genreselection.GenreSelectionScreen
import com.fenixgs.filmedodia.presentation.login.LoginScreen
import com.fenixgs.filmedodia.presentation.navigation.MainScreen
import com.fenixgs.filmedodia.presentation.register.RegisterScreen
import com.fenixgs.filmedodia.ui.theme.FilmeDoDiaTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FilmeDoDiaTheme {
                val navController = rememberNavController()
                val auth = FirebaseAuth.getInstance()
                val firestore = FirebaseFirestore.getInstance()
                val userPreferencesRepository = remember { UserPreferencesRepository(auth, firestore) }
                AppNavigation(navController, userPreferencesRepository)
            }
        }
    }
}

@Composable
fun AppNavigation(
    rootNavController: NavHostController,
    userPreferencesRepository: UserPreferencesRepository
) {
    val firebaseAuth = FirebaseAuth.getInstance()
    val currentUser = firebaseAuth.currentUser

    var startDestination by remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(currentUser) {
        if (currentUser == null) {
            startDestination = "login"
        } else {
            val hasGenres = userPreferencesRepository.hasPreferredGenres()
            startDestination = if (hasGenres) "main" else "genreSelection"
        }
    }

    if (startDestination == null) {
        // Pode colocar um loading aqui se quiser
        return
    }

    NavHost(navController = rootNavController, startDestination = startDestination!!) {
        composable("login") {
            LoginScreen(rootNavController)
        }
        composable("register") {
            RegisterScreen(rootNavController)
        }
        composable("genreSelection") {
            GenreSelectionScreen(
                rootNavController
            )
        }
        composable("main") {
            MainScreen(rootNavController)
        }
    }
}
