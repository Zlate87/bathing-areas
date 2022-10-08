package com.example.zlatko.beaches.domain

import com.example.zlatko.beaches.data.BathingAreas
import com.example.zlatko.beaches.data.berlin.BerlinBathingAreasRepository
import com.example.zlatko.beaches.data.brandenburg.BrandenburgBathingAreasRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetBathingAreaUseCase @Inject constructor(
    private val brandenburgRepository: BrandenburgBathingAreasRepository,
    private val berlinRepository: BerlinBathingAreasRepository,
    private val favoritesUseCase: FavoritesUseCase
) {
    suspend fun getBathingArea(id: String): BathingAreas? {
        val coroutineScope = CoroutineScope(Dispatchers.Default)
        val brandenburgBathingAreas =coroutineScope.async { brandenburgRepository.getBathingAreas() }
        val berlinBathingAreas = coroutineScope.async { berlinRepository.getBathingAreas() }
        val bathingAreas = brandenburgBathingAreas.await() + berlinBathingAreas.await()
        return bathingAreas
            .firstOrNull { it.id == id }
            ?.apply { favorite = favoritesUseCase.isFavorite(id) }
    }

}