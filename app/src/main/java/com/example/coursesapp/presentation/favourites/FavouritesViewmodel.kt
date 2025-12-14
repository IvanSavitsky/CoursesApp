package com.example.coursesapp.presentation.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coursesapp.data.LoadingState
import com.example.coursesapp.data.source.toDomain
import com.example.coursesapp.domain.courses.Course
import com.example.coursesapp.presentation.courses.CoursesScreenEvent
import com.example.coursesapp.presentation.courses.CoursesScreenUiCommand
import com.example.database.data.source.local.FavourtiteCourseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FavouritesViewmodel(
    val favouriteCourseDao: FavourtiteCourseDao
) : ViewModel() {
    val state = favouriteCourseDao.getAllAsFlow().map { favouriteCourseEntityList ->
        FavouritesScreenState(
            loadingState = LoadingState.Loaded(
                favouriteCourseEntityList.map {
                    it.toDomain()
                }
            )
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = FavouritesScreenState(
            loadingState = LoadingState.Loading
        )
    )

    val commands = Channel<CoursesScreenUiCommand>()

    fun onEvent(event: CoursesScreenEvent) {
        when (event) {
            is CoursesScreenEvent.OnToggleFavoriteClick -> {
                toggleFavorite(event.course)
            }

            is CoursesScreenEvent.OnCourseClick -> {
                commands.trySend(
                    CoursesScreenUiCommand.NavigateToCourseScreen(id = event.id)
                )
            }
        }
    }

    fun toggleFavorite(course: Course) {
        viewModelScope.launch(Dispatchers.IO) {
            if (course.hasLike) {
                favouriteCourseDao.deleteById(course.id)
            }
        }
    }
}