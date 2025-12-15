package com.example.coursesapp.domain.favourites

import com.example.coursesapp.domain.courses.Course
import com.example.database.data.FavouriteCourseEntity
import com.example.database.data.source.local.FavouriteCourseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ToggleFavouritesUseCase(
    private val favouriteCourseDao: FavouriteCourseDao
) {
    suspend operator fun invoke(course: Course) {
        withContext(Dispatchers.IO) {
            if (course.hasLike) {
                favouriteCourseDao.deleteById(course.id)
            } else {
                favouriteCourseDao.insert(
                    FavouriteCourseEntity(
                        id = course.id,
                        title = course.title,
                        text = course.text,
                        price = course.price,
                        rate = course.rate,
                        startDate = course.startDate,
                        hasLike = true,
                        publishDate = course.publishDate
                    )
                )
            }
        }
    }
}