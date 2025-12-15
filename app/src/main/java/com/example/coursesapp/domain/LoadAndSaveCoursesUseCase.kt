package com.example.coursesapp.domain

import com.example.coursesapp.data.source.toFavouriteCourseEntity
import com.example.coursesapp.domain.courses.Course
import com.example.coursesapp.domain.courses.CoursesRepository
import com.example.database.data.FavouriteCourseEntity
import com.example.database.data.source.local.FavouriteCourseDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf

class LoadAndSaveCoursesUseCase(
    private val coursesRepository: CoursesRepository,
    private val favouriteCourseDao: FavouriteCourseDao
) {
    suspend operator fun invoke(): Flow<List<Course>> {
        val courseList = coursesRepository.getAll()
        courseList.forEach {
            if (it.hasLike) {
                favouriteCourseDao.insert(
                    it.toFavouriteCourseEntity()
                )
            }
        }

        return combine(
            flowOf(courseList),
            favouriteCourseDao.getAllAsFlow(),
        ) { courseList, favorites ->
            buildCourseItems(courseList, favorites)
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