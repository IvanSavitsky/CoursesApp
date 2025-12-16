package com.example.domain.use_cases.courses

import com.example.domain.entity.Course
import com.example.domain.repository.CoursesRepository
import com.example.domain.repository.FavouritesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf

class LoadAndSaveCoursesUseCase(
    private val coursesRepository: CoursesRepository,
    private val favouritesRepository: FavouritesRepository
) {
    suspend operator fun invoke(): Flow<List<Course>> {
        val courseList = coursesRepository.getAll()
        courseList.forEach {
            if (it.hasLike) {
                favouritesRepository.insert(it)
            }
        }

        return combine(
            flowOf(courseList),
            favouritesRepository.getAllAsFlow(),
        ) { courseList, favorites ->
            buildCourseItems(courseList, favorites)
        }
    }

    private fun buildCourseItems(
        courseList: List<Course>,
        favorites: List<Course>
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