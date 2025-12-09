package com.example.coursesapp.presentation.courses

import com.example.coursesapp.data.LoadingState
import com.example.coursesapp.domain.courses.Course

data class CoursesScreenState(val loadingState: LoadingState<List<Course>>)