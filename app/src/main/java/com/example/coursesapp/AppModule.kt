package com.example.coursesapp

import android.content.Context.MODE_PRIVATE
import com.example.coursesapp.data.AuthRepositoryImpl
import com.example.coursesapp.data.source.CoursesRepositoryImpl
import com.example.coursesapp.domain.LoadAndSaveCoursesUseCase
import com.example.coursesapp.domain.SortCourseListByPublishDateUseCase
import com.example.coursesapp.domain.auth.AuthRepository
import com.example.coursesapp.domain.auth.CheckAuthUseCase
import com.example.coursesapp.domain.auth.CheckValidationUseCase
import com.example.coursesapp.domain.auth.LoginUseCase
import com.example.coursesapp.domain.auth.LogoutUseCase
import com.example.coursesapp.domain.courses.CoursesRepository
import com.example.coursesapp.domain.favourites.GetFavouritesUseCase
import com.example.coursesapp.domain.favourites.ToggleFavouritesUseCase
import com.example.coursesapp.presentation.account.AccountViewmodel
import com.example.coursesapp.presentation.courses.CoursesViewmodel
import com.example.coursesapp.presentation.courses.SortOrder
import com.example.coursesapp.presentation.favourites.FavouritesViewmodel
import com.example.coursesapp.presentation.login.LoginViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<CoursesRepository> {
        CoursesRepositoryImpl(coursesApi = get())
    }

    single {
        androidContext().getSharedPreferences("preferences", MODE_PRIVATE)
    }
    single<AuthRepository> {
        AuthRepositoryImpl(sharedPreferences = get())
    }

    single<LoadAndSaveCoursesUseCase> {
        LoadAndSaveCoursesUseCase(coursesRepository = get(), favouriteCourseDao = get())
    }

    single<ToggleFavouritesUseCase> {
        ToggleFavouritesUseCase(favouriteCourseDao = get())
    }

    single<GetFavouritesUseCase> {
        GetFavouritesUseCase(favouriteCourseDao = get())
    }

    single<SortCourseListByPublishDateUseCase> {
        SortCourseListByPublishDateUseCase()
    }

    single<CheckAuthUseCase> {
        CheckAuthUseCase(authRepository = get())
    }

    single<CheckValidationUseCase> {
        CheckValidationUseCase()
    }

    single<LoginUseCase> {
        LoginUseCase(authRepository = get())
    }
    single<LogoutUseCase> {
        LogoutUseCase(authRepository = get())
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