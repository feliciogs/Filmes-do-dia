package com.fenixgs.filmedodia.data.di

import com.fenixgs.filmedodia.data.api.OMDbApi
import com.fenixgs.filmedodia.data.repository.MovieRepository
import com.fenixgs.filmedodia.domain.usecase.GetMovieByTitleUseCase
import com.fenixgs.filmedodia.presentation.home.HomeViewModel
import com.fenixgs.filmedodia.presentation.login.LoginViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { FirebaseAuth.getInstance() }

    single { FirebaseFirestore.getInstance() }

    single { MovieRepository(get(), get()) }

    single { GetMovieByTitleUseCase(get()) }

    viewModel { HomeViewModel(get()) }
    viewModel { LoginViewModel() }
}
