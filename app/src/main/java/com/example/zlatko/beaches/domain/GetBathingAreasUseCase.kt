package com.example.zlatko.beaches.domain

import com.example.zlatko.beaches.data.BathingAreas
import com.example.zlatko.beaches.data.FavoritesRepository
import com.example.zlatko.beaches.data.berlin.BerlinBathingAreasRepository
import com.example.zlatko.beaches.data.brandenburg.BrandenburgBathingAreasRepository
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetBathingAreasUseCase @Inject constructor(
    private val brandenburgRepository: BrandenburgBathingAreasRepository,
    private val berlinRepository: BerlinBathingAreasRepository,
    private val favoritesRepository: FavoritesRepository
) {

    suspend fun getAllBathingAreas(
        withFeatures: Set<BathingAreas.Feature> = emptySet(),
        onlyIfFavorite: Boolean = false
    ): List<BathingAreas> {
        val coroutineScope = CoroutineScope(Dispatchers.Default)
        val brandenburgBathingAreas = coroutineScope.async {brandenburgRepository.getBathingAreas()}
        val berlinBathingAreas = coroutineScope.async {berlinRepository.getBathingAreas()}
        val bathingAreas = brandenburgBathingAreas.await() + berlinBathingAreas.await()
        val bathingAreaFavorites = favoritesRepository.getBathingAreaFavorites()
        return bathingAreas
            .filter { !onlyIfFavorite || bathingAreaFavorites.contains(it.id) }
            .filter { it.features.containsAll(withFeatures) }
    }
}