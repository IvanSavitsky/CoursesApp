package com.example.coursesapp

import android.content.Context.MODE_PRIVATE
import com.example.coursesapp.presentation.account.AccountViewmodel
import com.example.coursesapp.presentation.courses.CoursesViewmodel
import com.example.coursesapp.presentation.favourites.FavouritesViewmodel
import com.example.coursesapp.presentation.login.LoginViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single {
        androidContext().getSharedPreferences("preferences", MODE_PRIVATE)
    }

    viewModel<CoursesViewmodel> {
        CoursesViewmodel(
            loadAndSaveCoursesUseCase = get(),
            toggleFavouritesUseCase = get(),
            sortCoursesListByPublishDateUseCase = get()
        )
    }
    viewModel<FavouritesViewmodel> {
        FavouritesViewmodel(getFavouritesUseCase = get(), toggleFavouritesUseCase = get())
    }
    viewModel<LoginViewModel> {
        LoginViewModel(checkValidationUseCase = get(), loginUseCase = get())
    }
    viewModel<AccountViewmodel> {
        AccountViewmodel(logoutUseCase = get())
    }
}