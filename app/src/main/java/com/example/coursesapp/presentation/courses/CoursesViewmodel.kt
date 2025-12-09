package com.example.coursesapp.presentation.courses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coursesapp.data.LoadingState
import com.example.coursesapp.domain.courses.Course
import com.example.coursesapp.domain.courses.CoursesRepository
import com.example.database.data.FavouriteCourseEntity
import com.example.database.data.source.local.FavourtiteCourseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CoursesViewmodel(
    private val coursesRepository: CoursesRepository,
    private val favouriteCourseDao: FavourtiteCourseDao
) : ViewModel() {
    private val courseListFlow =
        MutableStateFlow<List<Course>>(
            emptyList()
        )

    private val listOrderState = MutableStateFlow<SortOrder>(SortOrder.DESC)

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
                toggleFavorite(event.course)
            }

            is CoursesScreenEvent.OnCourseClick -> {
                commands.trySend(CoursesScreenUiCommand.NavigateToCourseScreen(id = event.id))
            }
        }
    }

    fun sortByDate() {
        if (_state.value.loadingState is LoadingState.Loaded<List<Course>>) {
            _state.value = CoursesScreenState(
                loadingState = LoadingState.Loaded(
                    value = with((_state.value.loadingState as LoadingState.Loaded<List<Course>>)) {
                        when (listOrderState.value) {
                            SortOrder.ASC -> value.sortedBy { it.publishDate }
                            SortOrder.DESC -> value.sortedByDescending { it.publishDate }
                        }
                    }
                )
            )
        }
    }

    fun loadCourses() {
        _state.update { CoursesScreenState(LoadingState.Loading) }

        viewModelScope.launch(Dispatchers.IO) {
            try {
                courseListFlow.value = coursesRepository.getAll()

                combine(
                    courseListFlow,
                    favouriteCourseDao.getAllAsFlow(),
                ) { courseList, favorites ->
                    CoursesScreenState(
                        loadingState = LoadingState.Loaded(
                            value = buildCourseItems(
                                courseList,
                                favorites
                            )
                        )
                    )
                }.collect {
                    _state.value = it
                }
            } catch (exception: Exception) {
                _state.update { CoursesScreenState(LoadingState.Error(exception)) }
                commands.trySend(
                    CoursesScreenUiCommand.ShowErrorMessage(
                        message = exception.message.toString()
                    )
                )
            }
        }
    }

    fun toggleFavorite(course: Course) {
        viewModelScope.launch(Dispatchers.IO) {
            if (course.hasLike) {
                favouriteCourseDao.deleteById(course.id)
            } else {
                favouriteCourseDao.insert(
                    FavouriteCourseEntity(
                        id = course.id,
                        title = course.title,
                        text = course.text,
                        price = course.price,
                        rate = course.rate,
                        startDate = course.startDate,
                        hasLike = true,
                        publishDate = course.publishDate
                    )
                )
            }
        }
    }

    private fun buildCourseItems(
        courseList: List<Course>,
        favorites: List<FavouriteCourseEntity>
    ) = courseList.map { course ->
        Course(
            id = course.id,
            title = course.title,
            text = course.text,
            price = course.price,
            rate = course.rate,
            startDate = course.startDate,
            hasLike = if (course.hasLike) {
                true
            } else {
                favorites.firstOrNull { it.id == course.id } != null
            },
            publishDate = course.publishDate
        )
    }
}