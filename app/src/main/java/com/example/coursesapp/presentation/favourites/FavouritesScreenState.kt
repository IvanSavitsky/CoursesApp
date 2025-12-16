package com.example.coursesapp.presentation.favourites

import com.example.data.LoadingState
import com.example.domain.entity.Course

data class FavouritesScreenState(val loadingState: LoadingState<List<Course>>)