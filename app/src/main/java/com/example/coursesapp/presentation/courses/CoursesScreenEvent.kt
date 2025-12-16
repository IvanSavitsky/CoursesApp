package com.example.coursesapp.presentation.courses

import com.example.domain.entity.Course

interface CoursesScreenEvent {
    data class OnToggleFavoriteClick(val course: Course) : CoursesScreenEvent
    data class OnCourseClick(val id: String) : CoursesScreenEvent
    data object OnSortByDateClick: CoursesScreenEvent
}