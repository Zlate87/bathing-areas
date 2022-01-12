package com.example.zlatko.beaches.base.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.zlatko.beaches.base.repository.location.LocationDao
import com.example.zlatko.beaches.base.repository.location.model.LocationEntity

@Database(entities = [LocationEntity::class], version = 1)
@TypeConverters(OffsetDateTimeTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun locationDao(): LocationDao
}