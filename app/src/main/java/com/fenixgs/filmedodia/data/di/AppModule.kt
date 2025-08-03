package com.fenixgs.filmedodia.data.di

import com.fenixgs.filmedodia.data.api.OMDbApi
import com.fenixgs.filmedodia.data.repository.MovieRepository
import com.fenixgs.filmedodia.domain.usecase.GetMovieByTitleUseCase
import com.fenixgs.filmedodia.presentation.home.HomeViewModel
import com.fenixgs.filmedodia.presentation.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    single {
        Retrofit.Builder()
            .baseUrl("https://www.omdbapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OMDbApi::class.java)
    }

    single { MovieRepository(get()) }

    single { GetMovieByTitleUseCase(get()) }

    viewModel { HomeViewModel(get()) }
    viewModel { LoginViewModel() }
}
