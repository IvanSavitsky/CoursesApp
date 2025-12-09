package com.example.coursesapp.presentation.favourites

import com.example.coursesapp.data.LoadingState
import com.example.coursesapp.domain.courses.Course

data class FavouritesScreenState(val loadingState: LoadingState<List<Course>>)