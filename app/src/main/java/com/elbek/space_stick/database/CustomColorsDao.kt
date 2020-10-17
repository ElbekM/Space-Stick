package com.elbek.space_stick.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.elbek.space_stick.models.entities.RgbEntity

@Dao
interface CustomColorsDao {
    @Query("SELECT * FROM rgbentity")
    suspend fun getAll(): List<RgbEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNewColor(customColor: RgbEntity)
}
