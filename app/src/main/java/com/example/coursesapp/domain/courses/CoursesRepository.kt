package com.example.coursesapp.domain.courses

interface CoursesRepository {
    suspend fun getAll(): List<Course>
}