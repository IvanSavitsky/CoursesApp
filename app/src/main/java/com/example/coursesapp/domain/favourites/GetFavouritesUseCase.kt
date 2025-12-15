package com.example.coursesapp.domain.favourites

import com.example.coursesapp.data.source.toDomain
import com.example.database.data.source.local.FavouriteCourseDao
import kotlinx.coroutines.flow.map

class GetFavouritesUseCase(
    private val favouriteCourseDao: FavouriteCourseDao
) {
    operator fun invoke() = favouriteCourseDao.getAllAsFlow().map { favouriteCourseEntityList ->
        favouriteCourseEntityList.map {
            it.toDomain()
        }
    }
}