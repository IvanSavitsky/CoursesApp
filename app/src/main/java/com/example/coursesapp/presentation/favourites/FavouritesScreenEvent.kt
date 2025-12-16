package com.example.coursesapp.presentation.favourites

import com.example.domain.entity.Course

interface FavouritesScreenEvent {
    data class OnToggleFavoriteClick(val course: Course) : FavouritesScreenEvent
    data class OnCourseClick(val id: String) : FavouritesScreenEvent
}