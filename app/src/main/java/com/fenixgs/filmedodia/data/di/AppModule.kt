package com.fenixgs.filmedodia.data.di

import com.fenixgs.filmedodia.data.repository.MovieRepository
import com.fenixgs.filmedodia.data.repository.UserPreferencesRepository
import com.fenixgs.filmedodia.presentation.genreselection.GenreSelectionViewModel
import com.fenixgs.filmedodia.presentation.home.HomeViewModel
import com.fenixgs.filmedodia.presentation.login.LoginViewModel
import com.fenixgs.filmedodia.presentation.profile.ProfileViewModel
import com.fenixgs.filmedodia.presentation.register.RegisterViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { FirebaseAuth.getInstance() }

    single { FirebaseFirestore.getInstance() }

    single { MovieRepository(get(), get()) }

    single { UserPreferencesRepository(auth = get(), firestore = get()) }

    viewModel { HomeViewModel(get(),get()) }
    viewModel { LoginViewModel() }
    viewModel { RegisterViewModel() }
    viewModel { ProfileViewModel(get(), get()) }
    viewModel { GenreSelectionViewModel(get()) }
}
