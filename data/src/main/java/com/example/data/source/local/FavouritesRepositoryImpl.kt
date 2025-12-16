package com.example.data.source.local

import com.example.data.source.toDomain
import com.example.data.source.toFavouriteCourseEntity
import com.example.domain.entity.Course
import com.example.domain.repository.FavouritesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavouritesRepositoryImpl(
    val favouriteCourseDao: FavouriteCourseDao
) : FavouritesRepository {
    override fun getAllAsFlow(): Flow<List<Course>> {
        return favouriteCourseDao.getAllAsFlow().map { favouriteCourseEntityList ->
            favouriteCourseEntityList.map {
                it.toDomain()
            }
        }
    }

    override fun insert(course: Course) {
        favouriteCourseDao.insert(course.toFavouriteCourseEntity())
    }

    override fun deleteById(id: Int) {
        favouriteCourseDao.deleteById(id)
    }
}