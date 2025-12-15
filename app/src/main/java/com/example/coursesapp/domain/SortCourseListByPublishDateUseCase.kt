package com.example.coursesapp.domain

import com.example.coursesapp.domain.courses.Course
import com.example.coursesapp.presentation.courses.SortOrder

class SortCourseListByPublishDateUseCase {
    operator fun invoke(
        courseList: List<Course>,
        sortOrder: SortOrder
    ): List<Course> = when (sortOrder) {
        SortOrder.ASC -> courseList.sortedBy { it.publishDate }
        SortOrder.DESC -> courseList.sortedByDescending { it.publishDate }
    }
}