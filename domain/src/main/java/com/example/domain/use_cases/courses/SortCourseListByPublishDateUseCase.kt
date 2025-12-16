package com.example.domain.use_cases.courses

import com.example.domain.entity.Course

class SortCourseListByPublishDateUseCase {
    operator fun invoke(
        courseList: List<Course>,
        sortOrder: SortOrder
    ): List<Course> = when (sortOrder) {
        SortOrder.ASC -> courseList.sortedBy { it.publishDate }
        SortOrder.DESC -> courseList.sortedByDescending { it.publishDate }
    }
}

enum class SortOrder {
    ASC, DESC
}