package com.example.domain.repository

import com.example.domain.entity.Course

interface CoursesRepository {
    suspend fun getAll(): List<Course>
}