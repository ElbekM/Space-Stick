package com.elbek.space_stick.database

import com.elbek.space_stick.models.Rgb
import com.elbek.space_stick.models.entities.ColorInfoEntity
import com.elbek.space_stick.models.entities.RgbEntity
import com.elbek.space_stick.models.entities.toModel

class ColorDatabaseProvider(private val database: ColorDatabase) {

    suspend fun loadFromDatabase(): MutableList<Rgb>? =
        database.getColorDbDao()
            .getAll()?.colorRgb?.toModel()

    suspend fun addColorToDatabase(colorList: List<Rgb>) =
        database.getColorDbDao()
            .addNewColor(
                ColorInfoEntity(
                    colorRgb = RgbEntity.createRgbEntity(colorList)
                )
            )
}
