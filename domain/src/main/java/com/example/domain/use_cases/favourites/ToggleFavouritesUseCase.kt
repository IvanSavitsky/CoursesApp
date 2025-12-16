package com.example.domain.use_cases.favourites

import com.example.domain.repository.FavouritesRepository
import com.example.domain.entity.Course
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ToggleFavouritesUseCase(
    private val favouritesRepository: FavouritesRepository
) {
    suspend operator fun invoke(course: Course) {
        withContext(Dispatchers.IO) {
            if (course.hasLike) {
                favouritesRepository.deleteById(course.id)
            } else {
                favouritesRepository.insert(course)
            }
        }
    }
}