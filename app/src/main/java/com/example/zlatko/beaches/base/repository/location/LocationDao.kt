package com.example.zlatko.beaches.base.repository.location

import androidx.room.*
import com.example.zlatko.beaches.base.repository.location.model.LocationEntity

@Dao
interface LocationDao {

    companion object {
        private const val TTL = "1 day"
        private const val TRUE = 1
    }

    @Query("SELECT * FROM location where datetime(updated_at)>=datetime('now', '-$TTL')")
    suspend fun getAll(): List<LocationEntity>

    @Query("SELECT * FROM location where favorite = $TRUE")
    suspend fun getFavorites(): List<LocationEntity>

    @Query("SELECT * FROM location WHERE id = :id")
    suspend fun findById(id: String): LocationEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(locations: List<LocationEntity>)

    @Update
    suspend fun update(vararg locations: LocationEntity): Int

}