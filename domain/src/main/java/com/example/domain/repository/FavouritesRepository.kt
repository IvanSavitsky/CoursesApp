package com.example.domain.repository

import com.example.domain.entity.Course
import kotlinx.coroutines.flow.Flow

interface FavouritesRepository {
    fun getAllAsFlow(): Flow<List<Course>>
    fun insert(course: Course)
    fun deleteById(id: Int)
}