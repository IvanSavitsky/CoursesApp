package com.example.coursesapp.presentation.favourites

sealed interface FavouritesScreenUiCommand {
    data class ShowErrorMessage(
        val message: String
    ) : FavouritesScreenUiCommand

    data class NavigateToCourseScreen(
        val id: String
    ) : FavouritesScreenUiCommand
}