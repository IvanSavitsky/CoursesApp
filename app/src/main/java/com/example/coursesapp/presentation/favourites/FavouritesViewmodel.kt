package com.example.coursesapp.presentation.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coursesapp.presentation.courses.CoursesScreenUiCommand
import com.example.data.LoadingState
import com.example.domain.use_cases.favourites.GetFavouritesUseCase
import com.example.domain.use_cases.favourites.ToggleFavouritesUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FavouritesViewmodel(
    getFavouritesUseCase: GetFavouritesUseCase,
    private val toggleFavouritesUseCase: ToggleFavouritesUseCase
) : ViewModel() {
    val state = getFavouritesUseCase().map { courseList ->
        FavouritesScreenState(
            loadingState = LoadingState.Loaded(courseList)
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = FavouritesScreenState(
            loadingState = LoadingState.Loading
        )
    )

    val commands = Channel<CoursesScreenUiCommand>()

    fun onEvent(event: FavouritesScreenEvent) {
        when (event) {
            is FavouritesScreenEvent.OnToggleFavoriteClick -> {
                viewModelScope.launch {
                    toggleFavouritesUseCase(event.course)
                }
            }

            is FavouritesScreenEvent.OnCourseClick -> {
                commands.trySend(
                    CoursesScreenUiCommand.NavigateToCourseScreen(id = event.id)
                )
            }
        }
    }
}