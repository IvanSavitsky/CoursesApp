package com.example.coursesapp.presentation.courses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.LoadingState
import com.example.domain.use_cases.courses.LoadAndSaveCoursesUseCase
import com.example.domain.use_cases.courses.SortCourseListByPublishDateUseCase
import com.example.domain.use_cases.courses.SortOrder
import com.example.domain.entity.Course
import com.example.domain.use_cases.favourites.ToggleFavouritesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CoursesViewmodel(
    private val loadAndSaveCoursesUseCase: LoadAndSaveCoursesUseCase,
    private val toggleFavouritesUseCase: ToggleFavouritesUseCase,
    private val sortCoursesListByPublishDateUseCase: SortCourseListByPublishDateUseCase
) : ViewModel() {
    private val listOrderState = MutableStateFlow(SortOrder.DESC)

    private val _state =
        MutableStateFlow(CoursesScreenState(LoadingState.Loading))
    val state = _state.asStateFlow()

    init {
        loadCourses()
    }

    val commands = Channel<CoursesScreenUiCommand>()

    fun onEvent(event: CoursesScreenEvent) {
        when (event) {
            is CoursesScreenEvent.OnToggleFavoriteClick -> {
                viewModelScope.launch {
                    toggleFavouritesUseCase(event.course)
                }
            }

            is CoursesScreenEvent.OnCourseClick -> {
                commands.trySend(
                    CoursesScreenUiCommand.NavigateToCourseScreen(id = event.id)
                )
            }

            is CoursesScreenEvent.OnSortByDateClick -> {
                if (_state.value.loadingState is LoadingState.Loaded<List<Course>>) {
                    _state.value = CoursesScreenState(
                        loadingState = LoadingState.Loaded(
                            value = with(
                                (_state.value.loadingState as LoadingState.Loaded<List<Course>>)
                            ) {
                                sortCoursesListByPublishDateUseCase(
                                    courseList = value,
                                    sortOrder = listOrderState.value
                                )
                            }
                        )
                    )
                }
            }
        }
    }

    fun loadCourses() {
        _state.update { CoursesScreenState(LoadingState.Loading) }

        viewModelScope.launch(Dispatchers.IO) {
            try {
                loadAndSaveCoursesUseCase().collect {
                    _state.value = CoursesScreenState(
                        loadingState = LoadingState.Loaded(
                            value = it
                        )
                    )
                }
            } catch (exception: Exception) {
                exception.printStackTrace()
                _state.update { CoursesScreenState(LoadingState.Error(exception)) }
                commands.trySend(
                    CoursesScreenUiCommand.ShowErrorMessage(
                        message = exception.message.toString()
                    )
                )
            }
        }
    }
}