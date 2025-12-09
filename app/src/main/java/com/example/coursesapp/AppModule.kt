package com.example.coursesapp

import android.content.Context.MODE_PRIVATE
import com.example.coursesapp.data.AuthRepositoryImpl
import com.example.coursesapp.data.source.CoursesRepositoryImpl
import com.example.coursesapp.domain.auth.AuthRepository
import com.example.coursesapp.domain.courses.CoursesRepository
import com.example.coursesapp.presentation.account.AccountViewmodel
import com.example.coursesapp.presentation.courses.CoursesViewmodel
import com.example.coursesapp.presentation.favourites.FavouritesViewmodel
import com.example.coursesapp.presentation.login.LoginViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<CoursesRepository> {
        CoursesRepositoryImpl(coursesApi = get(), database = get())
    }

    single {
        androidContext().getSharedPreferences("preferences", MODE_PRIVATE)
    }
    single<AuthRepository> {
        AuthRepositoryImpl(sharedPreferences = get())
    }

    viewModel<CoursesViewmodel> {
        CoursesViewmodel(coursesRepository = get(), favouriteCourseDao = get())
    }
    viewModel<FavouritesViewmodel> {
        FavouritesViewmodel(favouriteCourseDao = get())
    }
    viewModel<LoginViewModel> {
        LoginViewModel(authRepository = get())
    }
    viewModel<AccountViewmodel> {
        AccountViewmodel(authRepository = get())
    }
}