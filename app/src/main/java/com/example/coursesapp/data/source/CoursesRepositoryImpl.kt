package com.example.coursesapp.data.source

import com.example.coursesapp.domain.courses.Course
import com.example.coursesapp.domain.courses.CoursesRepository
import com.example.database.data.FavouriteCourseEntity
import com.example.database.data.source.local.AppDatabase
import com.example.network.data.source.remote.CourseList.CourseInfo
import com.example.network.data.source.remote.CoursesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Locale

class CoursesRepositoryImpl(
    private val coursesApi: CoursesApi
) : CoursesRepository {
    override suspend fun getAll(): List<Course> {
        val courseList = withContext(Dispatchers.IO) {
            val responseBody = coursesApi.getCourseList()
            responseBody.courseInfoList.map { it.toDomain() }
        }
        return courseList
    }
}

fun CourseInfo.toDomain() = Course(
    id = id,
    title = title,
    text = text,
    price = price,
    rate = rate,
    startDate = startDate,
    hasLike = hasLike,
    publishDate = formatDate(publishDate)
)

fun FavouriteCourseEntity.toDomain() = Course(
    id = id,
    title = title,
    text = text,
    price = price,
    rate = rate,
    startDate = startDate,
    hasLike = hasLike,
    publishDate = publishDate
)

fun Course.toFavouriteCourseEntity() = FavouriteCourseEntity(
    id = id,
    title = title,
    text = text,
    price = price,
    rate = rate,
    startDate = startDate,
    hasLike = true,
    publishDate = publishDate
)

private fun formatDate(dateString: String): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        val date = inputFormat.parse(dateString)
        outputFormat.format(date ?: return dateString)
    } catch (e: Exception) {
        dateString
    }
}