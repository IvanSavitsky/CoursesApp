package com.example.coursesapp.presentation.courses

import com.example.data.LoadingState
import com.example.domain.entity.Course

data class CoursesScreenState(val loadingState: LoadingState<List<Course>>)