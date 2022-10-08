package com.example.zlatko.beaches.domain

import com.example.zlatko.beaches.data.FavoritesRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoritesUseCase @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) {
    suspend fun setFavorite(id: String, favorite: Boolean) {
        if (favorite) {
            favoritesRepository.addBathingAreaToFavorites(id)
        } else {
            favoritesRepository.removeBathingAreaFromFavorites(id)
        }
    }

    suspend fun isFavorite(id: String) = favoritesRepository.getBathingAreaFavorites().contains(id)
}