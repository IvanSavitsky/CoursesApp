package com.example.domain

import com.example.domain.use_cases.courses.LoadAndSaveCoursesUseCase
import com.example.domain.use_cases.courses.SortCourseListByPublishDateUseCase
import com.example.domain.use_cases.auth.CheckAuthUseCase
import com.example.domain.use_cases.auth.CheckValidationUseCase
import com.example.domain.use_cases.auth.LoginUseCase
import com.example.domain.use_cases.auth.LogoutUseCase
import com.example.domain.use_cases.favourites.GetFavouritesUseCase
import com.example.domain.use_cases.favourites.ToggleFavouritesUseCase
import org.koin.dsl.module

val domainModule = module {
    single<LoadAndSaveCoursesUseCase> {
        LoadAndSaveCoursesUseCase(coursesRepository = get(), favouritesRepository = get())
    }

    single<ToggleFavouritesUseCase> {
        ToggleFavouritesUseCase(favouritesRepository = get())
    }

    single<GetFavouritesUseCase> {
        GetFavouritesUseCase(favouritesRepository = get())
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
}