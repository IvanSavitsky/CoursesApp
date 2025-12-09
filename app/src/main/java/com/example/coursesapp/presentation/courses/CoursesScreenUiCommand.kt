package com.example.coursesapp.presentation.courses

sealed interface CoursesScreenUiCommand {
    data class ShowErrorMessage(
        val message: String
    ) : CoursesScreenUiCommand

    data class NavigateToCourseScreen(
        val id: String
    ) : CoursesScreenUiCommand
}