package com.example.zlatko.beaches.base.repository.location.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.OffsetDateTime

@Entity(tableName = "location")
data class LocationEntity(
    @PrimaryKey val id: String,
    @ColumnInfo val name: String,
    @ColumnInfo val longitude: Double,
    @ColumnInfo val latitude: Double,
    @ColumnInfo val temperature: String,
    @ColumnInfo(name = "last_measurement_date") val lastMeasurementDate: String,
    @ColumnInfo(name = "visibility_depth") val visibilityDepth: String,
    @ColumnInfo val rating: String?,
    @ColumnInfo val features: String,
    @ColumnInfo val favorite: Boolean,
    @ColumnInfo(name = "updated_at") val updatedAt: OffsetDateTime = OffsetDateTime.now()
)