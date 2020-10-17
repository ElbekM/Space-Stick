package com.elbek.space_stick.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.elbek.space_stick.models.entities.ColorInfoEntity

@Dao
interface ColorInfoDao {
    @Query("SELECT * FROM colorinfoentity")
    suspend fun getAll(): ColorInfoEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNewColor(colorInfo: ColorInfoEntity)
}
