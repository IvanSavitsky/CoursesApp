package com.example.domain.use_cases.favourites

import com.example.domain.repository.FavouritesRepository

class GetFavouritesUseCase(
    private val favouritesRepository: FavouritesRepository
) {
    operator fun invoke() = favouritesRepository.getAllAsFlow()
}