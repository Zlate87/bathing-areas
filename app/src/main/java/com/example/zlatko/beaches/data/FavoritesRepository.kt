package com.example.zlatko.beaches.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class FavoritesRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) {
    companion object {
        private val DATA_STORE_KEY = stringPreferencesKey("FAVORITES_REPOSITORY_DATA_STORE_KEY")
        private const val SEPARATOR = ", "
    }

    suspend fun addBathingAreaToFavorites(id: String) {
        dataStore.edit { preferences ->
            val newFavorites = getFavorites(preferences)
            newFavorites.add(id)
            preferences[DATA_STORE_KEY] = newFavorites.joinToString()
        }
    }

    suspend fun removeBathingAreaFromFavorites(id: String) {
        dataStore.edit { preferences ->
            val newFavorites = getFavorites(preferences)
            newFavorites.remove(id)
            preferences[DATA_STORE_KEY] = newFavorites.joinToString()
        }
    }

    suspend fun getBathingAreaFavorites(): List<String> {
        return dataStore.data.map { preferences ->
            (preferences[DATA_STORE_KEY] ?: "").split(SEPARATOR)
        }.first()
    }

    private fun getFavorites(preferences: MutablePreferences): HashSet<String> {
        val favorites: List<String> = (preferences[DATA_STORE_KEY] ?: "").split(SEPARATOR)
        val newFavorites = HashSet<String>()
        newFavorites.addAll(favorites)
        return newFavorites
    }
}